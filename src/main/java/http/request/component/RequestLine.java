package http.request.component;

import http.common.HttpMethod;
import http.common.ProtocolVersion;

public class RequestLine {

    private final HttpMethod httpMethod;
    private final RequestURI requestURI;
    private final ProtocolVersion protocolVersion;

    public RequestLine(HttpMethod httpMethod, RequestURI requestURI,
        ProtocolVersion protocolVersion) {
        this.httpMethod = httpMethod;
        this.requestURI = requestURI;
        this.protocolVersion = protocolVersion;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public RequestURI getRequestURI() {
        return requestURI;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", httpMethod, requestURI, protocolVersion);
    }
}
