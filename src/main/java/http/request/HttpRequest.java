package http.request;

import http.common.HttpMethod;
import http.common.version.ProtocolVersion;
import http.request.component.RequestHeader;
import http.request.component.RequestLine;
import http.request.component.RequestMessageBody;
import http.request.component.RequestParameter;
import http.request.component.RequestURI;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestMessageBody messageBody;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader,
        RequestMessageBody messageBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
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

    public RequestParameter getParameter() {
        return getRequestURI().getParameter();
    }

    public ProtocolVersion getProtocolVersion() {
        return requestLine.getProtocolVersion();
    }

    public Object getHeader(String key) {
        return requestHeader.getHeader(key);
    }

    public RequestMessageBody getMessageBody() {
        return messageBody;
    }

    @Override
    public String toString() {
        return String.join("\r\n", requestLine.toString(), requestHeader.toString(),
            messageBody.toString());
    }
}
