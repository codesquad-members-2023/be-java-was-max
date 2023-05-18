package http.request;

import http.common.HttpMethod;
import http.common.header.HeaderType;
import http.common.version.ProtocolVersion;
import http.request.component.RequestHeader;
import http.request.component.RequestLine;
import http.request.component.RequestMessageBody;
import http.request.component.RequestQueryString;
import http.request.component.RequestURI;
import java.util.Optional;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestQueryString queryString;
    private final RequestMessageBody messageBody;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestQueryString queryString,
        RequestMessageBody messageBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.queryString = queryString;
        this.messageBody = messageBody;
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

    public ProtocolVersion getProtocolVersion() {
        return requestLine.getProtocolVersion();
    }

    public Optional<String> get(HeaderType key) {
        return requestHeader.get(key);
    }

    public RequestMessageBody getMessageBody() {
        return messageBody;
    }

    @Override
    public String toString() {
        return String.join("\r\n", requestLine.toString(), requestHeader.toString(), messageBody.toString());
    }

}
