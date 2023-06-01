package webserver.http.request.component;

import webserver.http.common.HttpMethod;
import webserver.http.common.version.HttpVersion;
import webserver.http.common.version.ProtocolVersion;

import static webserver.http.parser.HttpRequestParser.*;

public class RequestLine {

    private final HttpMethod httpMethod;
    private final RequestURI requestURI;
    private final ProtocolVersion protocolVersion;

    public RequestLine(HttpMethod httpMethod, RequestURI requestURI, ProtocolVersion protocolVersion) {
        this.httpMethod = httpMethod;
        this.requestURI = requestURI;
        this.protocolVersion = protocolVersion;
    }

    public static RequestLine parseRequestLine(String requestLine) {
        HttpMethod httpMethod = HttpMethod.resolve(requestLine.split(REQUEST_LINE_SEPARATOR_REGEX)[HTTP_METHOD_INDEX]);
        String path = requestLine.split(REQUEST_LINE_SEPARATOR_REGEX)[REQUEST_URI_INDEX]
                .split(QUERYSTRING_SEPARATOR_REGEX)[PATH_INDEX];
        RequestURI requestURI = new RequestURI(path);
        String[] httpVersionTokens = requestLine.split(REQUEST_LINE_SEPARATOR_REGEX)[HTTP_VER_INDEX].split("[/.]");
        int major = Integer.parseInt(httpVersionTokens[1]);
        int minor = Integer.parseInt(httpVersionTokens[2]);
        ProtocolVersion httpVersion = new HttpVersion(major, minor);
        return new RequestLine(httpMethod, requestURI, httpVersion);
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
