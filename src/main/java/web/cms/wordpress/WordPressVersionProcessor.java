package web.cms.wordpress;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import okhttp3.Response;
import web.cms.joomla.annotation.JoomlaVersion;
import web.cms.wordpress.annotation.WordPressVersion;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.struct.Parser;

import java.util.Arrays;
import java.util.Optional;

public class WordPressVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final Parser parser;
    private final Destination destination;

    @Inject
    WordPressVersionProcessor(@Get Request request,
                              @WordPressVersion Parser parser,
                              @WordPressVersion Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        checkVersionViaPublicMetaInfo();
    }

    private void checkVersionViaPublicMetaInfo() {
        Integer[] codes = { 200 };

        String version = "unknown";
        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                version = parser.parse(body);
            }
        }
        destination.insert(0, String.format("  ** WordPress version (check #1) = %s", version));
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }
}
