package web.cms.wordpress;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.Destination;
import web.struct.Processor;

import java.util.Optional;

import static web.cms.CMSMarker.*;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class WordPressConnector extends AbstractCMSConnector {

    @Named(WORDPRESS_CHECK)
    private final Processor<CMSType> checkProcessor;
    @Named(WORDPRESS_VERSION)
    private final Processor<CMSType> versionProcessor;
    @Named(WORDPRESS_PLUGIN)
    private final Processor<CMSType> pluginProcessor;

    @Override
    public Pair<CMSType, Optional<Destination>> check() {
        checkProcessor.configure(params.getProtocol(), params.getServer());
        checkProcessor.process();
        return checkProcessor.transmit();
    }

    @Override
    public void checkVersion() {
        versionProcessor.configure(params.getProtocol(), params.getServer());
        versionProcessor.process();
        versionProcessor.transmit().getSecond().ifPresent(x -> x.fetch().forEach(System.out::println));
    }

    @Override
    public void checkPlugins() {
        pluginProcessor.configure(params.getProtocol(), params.getServer());
        pluginProcessor.process();
    }

}
