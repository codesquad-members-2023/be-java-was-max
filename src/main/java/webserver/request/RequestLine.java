package webserver.request;

import java.util.Map;

public class RequestLine {

    private final String method;
    private final String requestURI;
    private final RequestParameter requestParameter;
    private final String httpVersion;

    public RequestLine(String method, String requestURI, RequestParameter requestParameter,
        String httpVersion) {
        this.method = method;
        this.requestURI = requestURI;
        this.requestParameter = requestParameter;
        this.httpVersion = httpVersion;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public RequestParameter getRequestParameter() {
        return requestParameter;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return String.format("%s %s%s %s", method, requestURI, requestParameter, httpVersion);
    }
}
