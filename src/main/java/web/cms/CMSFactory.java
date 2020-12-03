package web.cms;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.module.*;
import web.module.provider.*;
import web.struct.Connector;

public class CMSFactory {

    public static Connector getCMSConnector(CMSType cmsType) {
        Injector injector = Guice.createInjector(
                new CMSModule()
        );

        switch (cmsType) {
            case JOOMLA:
                return injector.getInstance(JoomlaProvider.class).get();
            case WORDPRESS:
                return injector.getInstance(WordPressProvider.class).get();
            case YII:
                return injector.getInstance(YiiProvider.class).get();
            case DATALIFE_ENGINE:
                return injector.getInstance(DataLifeProvider.class).get();
            case MAXSITE_CMS:
                return injector.getInstance(MaxSiteProvider.class).get();
            case DRUPAL:
                return injector.getInstance(DrupalProvider.class).get();
            case BITRIX:
                return injector.getInstance(BitrixProvider.class).get();
            case MODX:
                return injector.getInstance(ModXProvider.class).get();
            case LAVAREL:
                return injector.getInstance(LavarelProvider.class).get();
            case TILDA:
                return injector.getInstance(TildaProvider.class).get();
            case VAM_SHOP:
                return injector.getInstance(VamShopProvider.class).get();
            case NUXT_JS:
                return injector.getInstance(NuxtProvider.class).get();
            case MAGENTO:
                return injector.getInstance(MagentoProvider.class).get();
            case OPENCART:
                return injector.getInstance(OpenCartProvider.class).get();
            case INSALES:
                return injector.getInstance(InSalesProvider.class).get();
            default:
                throw new IllegalArgumentException("Unsupported CMS type");
        }
    }

}
