package web.env.php;

import lombok.RequiredArgsConstructor;
import web.env.AbstractEnvConnector;
import web.env.EnvType;
import web.struct.Processor;

@RequiredArgsConstructor
public class PhpConnector extends AbstractEnvConnector {

    private final Processor<EnvType> processor;

    @Override
    public void checkVersion() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
    }

}
