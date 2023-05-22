package http.request;

import http.HttpHeader;
import http.HttpBody;

public class HttpRequest {
    private RequestLine requestLine;
    private HttpHeader headers;
    private HttpBody body;

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

    public HttpBody getBody() {
        return body;
    }

    public void setBody(final HttpBody body) {
        this.body = body;
    }
}
