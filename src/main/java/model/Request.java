package model;

public class Request {

    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private final Body body;

    public Request(RequestLine requestLine, RequestHeaders requestHeaders, Body body) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.body = body;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeaders getHeaders() {
        return requestHeaders;
    }

    public Body getBody() {
        return body;
    }
}
