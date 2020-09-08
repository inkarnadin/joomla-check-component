package web.joomla.processor;

import web.IProcessor;
import web.IRequest;
import web.ExtensionStorage;
import web.ResultStorage;
import web.joomla.request.JoomlaCheckComponentRequest;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

public class JoomlaCheckComponentProcessor implements IProcessor {

    private IRequest request = new JoomlaCheckComponentRequest();
    private ExtensionStorage storage = new ExtensionStorage();

    private final String protocol;
    private final String url;

    public JoomlaCheckComponentProcessor(String protocol, String url) {
        this.protocol = protocol;
        this.url = url;

        storage.feedJoomlaComponents();
    }

    @Override
    public void process() {
        List<String> extensions = storage.getComponents();

        if (chechJoomla()) {
            System.out.println("Not the Joomla-build site!");
            return;
        }

        int success = 0;
        int failure = 0;
        int remain = extensions.size();
        int error = 0;

        List<String> result = new ArrayList<>();
        for (String ext : extensions) {
            try {
                remain--;
                Response response = request.send(protocol, url, ext);
                if (response.code() == 200) {
                    result.add(ext);
                    success++;
                } else {
                    failure++;
                }
                response.close();
            } catch (Exception e) {
                error++;
            }
            System.out.print(String.format("\rRemain: %1s, found: %2s, not found: %3s, exception: %4s", remain, success, failure, error));
        }
        ResultStorage.save(null, result);
    }

    private boolean chechJoomla() {
        Response response = request.send(protocol, url, "com_exactly_not_existing");
        response.close();
        return response.code() == 200;
    }

}
