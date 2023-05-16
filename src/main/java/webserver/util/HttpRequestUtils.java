package webserver.util;

import model.RequestLine;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final String BLANK = " ";
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS_MARK = "=";


    private HttpRequestUtils() {
    }

    public static RequestLine parseLine(String requestLine) {
        return new RequestLine(parseMethod(requestLine), parseUrl(requestLine), parseQueryString(requestLine));
    }

    public static String parseUrl(String requestLine) {
        String url = extractUrl(requestLine);

        if (url.contains(QUESTION_MARK)) {
            url = url.split("\\?")[METHOD_INDEX];
        }

        if (url.equals("/")) {
            url = "/index.html";
        }

        return url;
    }

    public static String parseMethod(String requestLine) {
        return requestLine.split(BLANK)[METHOD_INDEX];
    }

    public static Map<String, String> parseQueryString(String requestLine) {
        String url = extractUrl(requestLine);

        if (!url.contains(QUESTION_MARK)) {
            return null;
        }

        String[] params = url.split("\\?")[URL_INDEX].split(AMPERSAND);

        Map<String, String> queryMap = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split(EQUALS_MARK);
            queryMap.put(keyValue[METHOD_INDEX], keyValue[URL_INDEX]);
        }

        return queryMap;
    }

    private static String extractUrl(String requestLine) {
        return requestLine.split(BLANK)[URL_INDEX];
    }
}
