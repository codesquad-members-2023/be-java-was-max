package webserver.util;

import model.RequestLine;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;

    private HttpRequestUtils() {
    }

    public static RequestLine parseLine(String requestLine) {
        return new RequestLine(parseMethod(requestLine), parseUrl(requestLine), parseQueryString(requestLine));
    }

    public static String parseUrl(String requestLine) {
        String url = extractUrl(requestLine);

        if (url.contains("?")) {
            url = url.split("\\?")[METHOD_INDEX];
        }

        if (url.equals("/")) {
            url = "/index.html";
        }

        return url;
    }

    public static String parseMethod(String requestLine) {
        return requestLine.split(" ")[METHOD_INDEX];
    }

    public static Map<String, String> parseQueryString(String requestLine) {
        String url = extractUrl(requestLine);

        if (!url.contains("?")) {
            return null;
        }

        String[] params = url.split("\\?")[URL_INDEX].split("&");

        Map<String, String> queryMap = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            queryMap.put(keyValue[METHOD_INDEX], keyValue[URL_INDEX]);
        }

        return queryMap;
    }

    private static String extractUrl(String requestLine) {
        return requestLine.split(" ")[URL_INDEX];
    }
}
