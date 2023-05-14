package http.request;

import http.common.HttpMethod;
import http.common.ProtocolVersion;
import http.request.component.RequestHeader;
import http.request.component.RequestLine;
import http.request.component.RequestParameter;
import http.request.component.RequestURI;

public class HttpServletRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;

    public HttpServletRequest(RequestLine requestLine, RequestHeader requestHeader) {
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

    public ProtocolVersion getProtocolVersion() {
        return requestLine.getProtocolVersion();
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
