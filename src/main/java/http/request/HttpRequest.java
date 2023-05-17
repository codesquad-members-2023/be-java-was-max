package http.request;

import http.HttpBody;
import http.HttpHeader;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private QueryString queryString;
    private HttpHeader headers;
    private HttpBody body;

    public HttpRequest() {
    }

    public HttpRequestLine getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(HttpRequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public QueryString getQueryString() {
        return queryString;
    }

    public void setQueryString(final QueryString queryString) {
        this.queryString = queryString;
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
