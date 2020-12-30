package web.cms.moguta;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class MogutaVersionProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.MOGUTA_CMS;

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination);
        versionAnalyzer.prepare(protocol, server, CMSType.MOGUTA_CMS);
        versionAnalyzer.checkViaPageKeywords("mg-admin", new Pattern[] {
                Pattern.compile("<!--.*VER v(.*)\\s-->")
        });
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}