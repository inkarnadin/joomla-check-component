package web.cms.joomla;

import lombok.RequiredArgsConstructor;
import web.cms.AbstractConnector;
import web.PluginProcessor;

@RequiredArgsConstructor
public class JoomlaConnector extends AbstractConnector {

    private final PluginProcessor pluginProcessor;

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void checkPlugins() {
        pluginProcessor.configure(protocol, host);
        pluginProcessor.process();
    }

}