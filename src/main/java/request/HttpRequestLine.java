package request;

public class HttpRequestLine {
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;
    private static final String  START_LINE_DIVIDER = " ";
    private static final String QUERY_STRING = "?";
    private final String method;
    private final String url;
    private QueryString queryString;
    private final String protocol;

    public HttpRequestLine(String startLine) {
        String[] tokens = startLine.split(START_LINE_DIVIDER);
        this.method = tokens[METHOD_INDEX];
        this.protocol = tokens[PROTOCOL_INDEX];

        if (startLine.contains(QUERY_STRING)) { // if queryString exists
            this.url = tokens[URL_INDEX].split("\\?")[0];
            this.queryString = new QueryString(tokens[1].split("\\?")[1]);
            return;
        }

        this.url = tokens[URL_INDEX];
    }

    public String getMethod() {
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
