package request;

import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String url;
    private Map<String, String> queryString;
    private final String protocol;

    public HttpRequest(String startLine) {
        String[] tokens = startLine.split(" ");
        this.method = tokens[0];
        this.protocol = tokens[2];

        if (startLine.contains("?")) { // if queryString exists
            this.url = tokens[1].split("\\?")[0];
            this.queryString = HttpRequestUtils.parseQueryString(tokens[1].split("\\?")[1]);
            return;
        }

        this.url = tokens[1];
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getQueryString() {
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
