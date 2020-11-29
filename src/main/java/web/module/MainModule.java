package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import lombok.SneakyThrows;
import web.cms.CMSChecker;
import web.cms.CMSDeterminant;
import web.cms.CMSType;
import web.cms.bitrix.BitrixCheckProcessor;
import web.cms.bitrix.annotation.Bitrix;
import web.cms.datalife.DataLifeCheckProcessor;
import web.cms.datalife.annotation.DataLife;
import web.cms.drupal.DrupalCheckProcessor;
import web.cms.drupal.annotation.Drupal;
import web.cms.joomla.JoomlaCheckProcessor;
import web.cms.joomla.annotation.Joomla;
import web.cms.lavarel.LavarelCheckProcessor;
import web.cms.lavarel.annotation.Lavarel;
import web.cms.maxsite.MaxSiteCheckProcessor;
import web.cms.maxsite.annotation.MaxSite;
import web.cms.modx.ModXCheckProcessor;
import web.cms.modx.annotation.ModX;
import web.cms.wordpress.WordPressCheckProcessor;
import web.cms.wordpress.annotation.WordPress;
import web.cms.yii.YiiCheckProcessor;
import web.cms.yii.annotation.Yii;
import web.db.DBAdminChecker;
import web.http.GetRequest;
import web.http.Request;
import web.module.annotation.Cms;
import web.module.annotation.DBAdmin;
import web.parser.BooleanReturnTextParser;
import web.parser.TextParser;
import web.struct.*;

public class MainModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).to(GetRequest.class);

        bind(new TypeLiteral<TextParser<Boolean>>(){}).to(BooleanReturnTextParser.class);
        bind(Destination.class).to(SimpleDestination.class).in(Scopes.NO_SCOPE);

        bind(Processor.class).annotatedWith(Joomla.class).to(JoomlaCheckProcessor.class);
        bind(Processor.class).annotatedWith(WordPress.class).to(WordPressCheckProcessor.class);
        bind(Processor.class).annotatedWith(Yii.class).to(YiiCheckProcessor.class);
        bind(Processor.class).annotatedWith(DataLife.class).to(DataLifeCheckProcessor.class);
        bind(Processor.class).annotatedWith(MaxSite.class).to(MaxSiteCheckProcessor.class);
        bind(Processor.class).annotatedWith(Drupal.class).to(DrupalCheckProcessor.class);
        bind(Processor.class).annotatedWith(Bitrix.class).to(BitrixCheckProcessor.class);
        bind(Processor.class).annotatedWith(ModX.class).to(ModXCheckProcessor.class);
        bind(Processor.class).annotatedWith(Lavarel.class).to(LavarelCheckProcessor.class);

        bind(new TypeLiteral<Determinant<CMSType, Destination>>(){}).to(CMSDeterminant.class);

        bind(Checker.class).annotatedWith(Cms.class).to(CMSChecker.class);

        bind(Checker.class).annotatedWith(DBAdmin.class).to(DBAdminChecker.class);

        bind(Runner.class);
    }

}
