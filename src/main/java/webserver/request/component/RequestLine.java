package webserver.request.component;

import webserver.request.common.HttpMethod;
import webserver.response.HttpVersion;

public class RequestLine {

    private final HttpMethod httpMethod;
    private final RequestURI requestURI;
    private final HttpVersion httpVersion;

    public RequestLine(HttpMethod httpMethod, RequestURI requestURI,
        HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.requestURI = requestURI;
        this.httpVersion = httpVersion;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public RequestURI getRequestURI() {
        return requestURI;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", httpMethod, requestURI, httpVersion);
    }
}
