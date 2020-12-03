package web.cms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CMSType {

    WORDPRESS(0, "WordPress"),
    JOOMLA(1, "Joomla!"),
    YII(2, "Yii Framework"),
    DATALIFE_ENGINE(3, "DataLife Engine"),
    MAXSITE_CMS(4, "MaxSite CMS"),
    DRUPAL(5, "Drupal"),
    BITRIX(6, "1C-Bitrix"),
    MODX(7, "MODx"),
    LAVAREL(8, "Lavarel"),
    TILDA(9, "Tilda"),
    VAM_SHOP(10, "VamShop"),
    NUXT_JS(11, "Nuxt.js"),
    MAGENTO(12, "Magento"),
    OPENCART(13, "OpenCart"),
    INSALES(14, "InSales"),
    UNKNOWN(-1, "Unknown");

    private final int id;
    private final String name;

}
