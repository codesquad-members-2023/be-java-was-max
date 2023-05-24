package model;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final String BLANK = " ";
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS_MARK = "=";

    private final String method;
    private final ContentType contentType;
    private final String url;
    private final Map<String, String> queryMap;

    public RequestLine(String requestLine) {
        this.method = parseMethod(requestLine);
        this.url = parseUrl(requestLine);
        this.contentType = ContentType.findByUrl(url);
        this.queryMap = parseQueryString(requestLine);
    }

    private String parseUrl(String requestLine) {
        String url = requestLine.split(BLANK)[URL_INDEX];

        if (url.contains(QUESTION_MARK)) {
            url = url.split("\\?")[METHOD_INDEX];
        }

        if (url.equals("/")) {
            url = "/index.html";
        }

        return url;
    }

    private String parseMethod(String requestLine) {
        return requestLine.split(BLANK)[METHOD_INDEX];
    }

    private Map<String, String> parseQueryString(String requestLine) {
        String url = requestLine.split(BLANK)[URL_INDEX];

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

    public String getMethod() {
        return method;
    }

    public Map<String, String> getQueryMap() {
        return queryMap;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getUrl() {
        return url;
    }
}
