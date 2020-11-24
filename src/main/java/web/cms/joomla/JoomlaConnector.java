package web.cms.joomla;

import lombok.RequiredArgsConstructor;
import web.struct.Processor;
import web.cms.AbstractCMSConnector;

@RequiredArgsConstructor
public class JoomlaConnector extends AbstractCMSConnector {

    private final Processor versionProcessor;
    private final Processor pluginProcessor;

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void checkVersion() {
        versionProcessor.configure(params.getProtocol(), params.getServer());
        versionProcessor.process();
        versionProcessor.transmit().ifPresent(x -> System.out.println(x.fetch().get(0)));
    }

    @Override
    public void checkPlugins() {
        pluginProcessor.configure(params.getProtocol(), params.getServer());
        pluginProcessor.process();
    }

}
