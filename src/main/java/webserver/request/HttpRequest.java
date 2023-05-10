package webserver.request;

import webserver.request.common.HttpMethod;
import webserver.request.component.RequestHeader;
import webserver.request.component.RequestLine;
import webserver.request.component.RequestParameter;
import webserver.request.component.RequestURI;
import webserver.response.HttpVersion;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public RequestURI getRequestURI() {
        return requestLine.getRequestURI();
    }

    public String getPath() {
        return getRequestURI().getPath();
    }

    public RequestParameter getParameter() {
        return getRequestURI().getParameter();
    }

    public HttpVersion getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public Object getHeader(String key) {
        return requestHeader.getHeader(key);
    }

    public void addHeader(String key, Object value) {
        requestHeader.addHeader(key, value);
    }

    @Override
    public String toString() {
        return String.format("%s\r\n%s", requestLine, requestHeader);
    }
}
