package web.analyzer;

import static web.http.ContentType.*;

public class AnalyzeConst {

    public static final String[] BASE_PATH = new String[] { "" };

    public static final Integer[] SUCCESS_CODES = new Integer[] { 200, 304 };
    public static final Integer[] DENIED_CODES = new Integer[] { 401, 403 };
    public static final Integer[] ACCEPT_CODES = new Integer[] { 200, 304, 401, 403 };

    public static final String[] SCRIPT_FILES = new String[] { APPLICATION_JAVASCRIPT, APPLICATION_X_JAVASCRIPT, TEXT_JAVASCRIPT };
    public static final String[] IMAGE_FILES = new String[] { IMAGE_PNG, IMAGE_JPG };
    public static final String[] XML_FILES = new String[] { APPLICATION_XML, TEXT_XML };
    public static final String[] JSON_FILES = new String[] { APPLICATION_JSON };

}
