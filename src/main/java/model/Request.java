package model;

public class Request {

    private final RequestLine requestLine;
    private final Headers headers;
    private final Body body;

    public Request(RequestLine requestLine, Headers headers, Body body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public Body getBody() {
        return body;
    }
}
