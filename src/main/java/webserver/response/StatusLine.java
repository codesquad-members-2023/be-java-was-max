package webserver.response;

public class StatusLine {

    private HttpVersion httpVersion;
    private StatusCode statusCode;

    public StatusLine(HttpVersion httpVersion, StatusCode statusCode) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return String.format("%s %s", httpVersion, statusCode);
    }
}
