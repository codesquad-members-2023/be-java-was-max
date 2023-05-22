package http.response;

import http.HttpBody;
import http.HttpHeader;

public class HttpResponse {
    private StatusLine statusLine;
    private HttpHeader headers;
    private HttpBody body;

    public HttpResponse() {
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public HttpHeader getHeaders() {
        return headers;
    }

    public HttpBody getBody() {
        return body;
    }

    public void setStatusLine(final StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public void setHeaders(final HttpHeader headers) {
        this.headers = headers;
    }

    public void setBody(final HttpBody body) {
        this.body = body;
    }
}
