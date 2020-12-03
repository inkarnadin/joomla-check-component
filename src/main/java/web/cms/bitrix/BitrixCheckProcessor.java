package web.cms.bitrix;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class BitrixCheckProcessor extends AbstractProcessor {

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
                "bitrix/cache",
                "bitrix/js",
                "bitrix/tools",
                "bitrix/components"
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
        pageAnalyzer.checkViaPageHeaderValues(HIGH, "bitrix/admin" , new String[] { "x-devsrv-cms", "x-powered-cms" }, new Pattern[] {
                Pattern.compile("Bitrix")
        });
        pageAnalyzer.checkViaPageCookies(HIGH, new String[] { "" }, Pattern.compile("BITRIX_SM_GUEST_ID"));

        assign(destination, result, CMSType.BITRIX);
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
