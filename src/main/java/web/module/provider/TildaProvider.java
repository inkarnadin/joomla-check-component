package web.module.provider;

import com.google.inject.Provider;
import web.cms.tilda.TildaConnector;
import web.struct.Connector;

public class TildaProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new TildaConnector();
    }

}
