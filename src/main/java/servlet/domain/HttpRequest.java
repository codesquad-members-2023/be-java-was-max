package servlet.domain;


import java.util.Map;

public class HttpRequest {
    private final StartLine startLine;

    private final RequestHeaders requestHeaders;

    private HttpBody body;


    private HttpRequest(StartLine startLine, RequestHeaders requestHeaders, HttpBody body) {
        this.startLine = startLine;
        this.requestHeaders = requestHeaders;
        this.body = body;
    }

    public static HttpRequest of(StartLine startLine, RequestHeaders requestHeaders, HttpBody body) {
        return new HttpRequest(startLine, requestHeaders, body);
    }

    private HttpRequest(StartLine startLine, RequestHeaders requestHeaders) {
        this.startLine = startLine;
        this.requestHeaders = requestHeaders;
    }

    public static HttpRequest of(StartLine startLine, RequestHeaders requestHeaders) {
        return new HttpRequest(startLine, requestHeaders);
    }


    public String getUrl() {
        return startLine.getUrl();
    }

    public Map<String, String> getParameters() {
        if (body != null) {
            return body.getKeyValue();
        }
        return startLine.getParameters();
    }


    public boolean isMatching(int count, String path, String httpRequestMethod) {
        return startLine.isSamePath(path) && (startLine.isSameParameterCount(count) || body.isSameCount(count)) && startLine.isSameMethod(httpRequestMethod);
    }

    public boolean hasParameters() {
        return startLine.hasParameter() || body != null;
    }
}
