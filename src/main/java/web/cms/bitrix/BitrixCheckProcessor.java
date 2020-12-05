package web.cms.bitrix;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.HeaderAnalyzer;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.cms.AbstractCMSProcessor;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.*;
import static web.http.ContentType.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class BitrixCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.BITRIX;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM, new Pattern[] {
                Pattern.compile("bitrix/cache"),
                Pattern.compile("bitrix/js"),
                Pattern.compile("bitrix/tools"),
                Pattern.compile("bitrix/components"),
                Pattern.compile("bitrix/panel")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaPaths(LOW, new Integer[] { 200, 401, 403 }, new String[] {
                "bitrix/admin",
                "bitrix/cache",
                "bitrix/js",
                "bitrix/tools",
                "bitrix/components"
        });
        pathAnalyzer.checkViaFiles(HIGH, new Integer[] { 200, 304 }, new String[] { TEXT_XML, APPLICATION_XML }, new String[] {
                "bitrix/p3p.xml"
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "bitrix/admin" }, new Pattern[] {
                Pattern.compile("bx-admin-prefix"),
                Pattern.compile("BX\\.message"),
                Pattern.compile("BX\\.addClass"),
                Pattern.compile("BX\\.removeClass"),
                Pattern.compile("BX\\.ready"),
                Pattern.compile("BX\\.adminLogin"),
                Pattern.compile("AUTH_NEW_PASSWORD_CONFIRM_WRONG")
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(protocol, server, result);
        headerAnalyzer.checkViaCookies(HIGH, new String[] { "" }, new Pattern[] {
                Pattern.compile("BITRIX_SM_GUEST_ID"),
                Pattern.compile("BITRIX_SM_LAST_VISIT"),
                Pattern.compile("BITRIX_SM_ABTEST")
        });
        headerAnalyzer.checkViaHeaderValues(HIGH, new String[] { "bitrix/admin" }, new Pattern[] {
                Pattern.compile("Bitrix")
        });

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
