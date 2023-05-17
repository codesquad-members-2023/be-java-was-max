package servlet.domain;


import servlet.domain.request.target.Path;

import java.lang.reflect.Method;
import java.util.Map;

public class HttpRequest {
    private final StartLine startLine;

    private final RequestHeaders requestHeaders;

    private String body;


    private HttpRequest(StartLine startLine, RequestHeaders requestHeaders, String body) {
        this.startLine = startLine;
        this.requestHeaders = requestHeaders;
        this.body = body;
    }

    private HttpRequest(StartLine startLine, RequestHeaders requestHeaders) {
        this.startLine = startLine;
        this.requestHeaders = requestHeaders;
    }

    public static HttpRequest of(StartLine startLine, RequestHeaders requestHeaders) {
        return new HttpRequest(startLine, requestHeaders);
    }


    public String getBody() {
        return body;
    }

    public String getUrl() {
        return startLine.getUrl();
    }

    public Map<String, String> getParameters() {
        return startLine.getParameters();
    }

    public Path getPath() {
        return startLine.getPath();
    }

    public boolean isMatching(Method method, String path) {
        if (startLine.hasParameter()) {
            return startLine.isSamePath(path) && startLine.isSameParameterCount(method.getParameterCount());
        }
        return startLine.isSamePath(path);
    }

    public boolean hasParameters() {
        return startLine.hasParameter();
    }
}
