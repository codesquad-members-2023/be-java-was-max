package http.request;

import http.HttpHeader;

public class HttpRequest {
    private RequestLine requestLine;
    private HttpHeader headers;
    private RequestBody body;

    public HttpRequest() {
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public HttpHeader getHeaders() {
        return headers;
    }

    public void setHeaders(final HttpHeader headers) {
        this.headers = headers;
    }

    public RequestBody getBody() {
        return body;
    }

    public void setBody(final RequestBody body) {
        this.body = body;
    }
}
