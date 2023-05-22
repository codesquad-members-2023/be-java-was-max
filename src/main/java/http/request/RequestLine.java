package http.request;

import http.HttpMethod;
import http.HttpUtils;

public class RequestLine {
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;
    private static final String  START_LINE_DIVIDER = " ";
    private static final String QUERY_STRING = "?";
    private static final String QUERY_STRING_REGEX = "\\?";
    private final HttpMethod method;
    private final String url;
    private QueryString queryString;
    private final String protocol;

    public RequestLine(String startLine) {
        String[] tokens = startLine.split(START_LINE_DIVIDER);
        this.method = HttpUtils.getMethodType(tokens[METHOD_INDEX]);
        this.protocol = tokens[PROTOCOL_INDEX];

        if (startLine.contains(QUERY_STRING)) { // if queryString exists
            this.url = tokens[URL_INDEX].split(QUERY_STRING_REGEX)[0];
            this.queryString = new QueryString(tokens[1].split(QUERY_STRING_REGEX)[1]);
            return;
        }

        this.url = tokens[URL_INDEX];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public QueryString getQueryString() {
        return queryString;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return "HttpRequest [method=" + method + ", url=" + url + ", queryString=" + queryString + ", protocol=" + protocol + "]";
    }
}
