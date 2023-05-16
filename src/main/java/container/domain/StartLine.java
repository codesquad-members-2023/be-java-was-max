package container.domain;

public class StartLine {
    private final HttpRequestMethod httpRequestMethod;
    private final Url uri;
    private final HttpVersion httpVersion;

    public StartLine(HttpRequestMethod httpRequestMethod, Url uri, HttpVersion httpVersion) {
        this.httpRequestMethod = httpRequestMethod;
        this.uri = uri;
        this.httpVersion = httpVersion;
    }

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public Url getUri() {
        return uri;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }
}
