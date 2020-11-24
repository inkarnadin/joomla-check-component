package web.cms.wordpress;

import lombok.RequiredArgsConstructor;
import web.struct.Processor;
import web.cms.AbstractCMSConnector;

@RequiredArgsConstructor
public class WordPressConnector extends AbstractCMSConnector {

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
        versionProcessor.transmit().ifPresent(x -> x.fetch().forEach(System.out::println));
    }

    @Override
    public void checkPlugins() {
        pluginProcessor.configure(params.getProtocol(), params.getServer());
        pluginProcessor.process();
    }

}
