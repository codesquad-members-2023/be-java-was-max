package http.response;

import http.HttpHeader;

public class HttpResponse {
    private StatusLine statusLine;
    private HttpHeader headers;
    private byte[] body;

    public HttpResponse() {
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public HttpHeader getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setStatusLine(final StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public void setHeaders(final HttpHeader headers) {
        this.headers = headers;
    }

    public void setBody(final byte[] body) {
        this.body = body;
    }
}
