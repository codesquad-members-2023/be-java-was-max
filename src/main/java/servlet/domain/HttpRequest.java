package servlet.domain;


public class HttpRequest {
    private final StartLine startLine;

    private final Headers headers;

    private final String body;


    public HttpRequest(StartLine startLine, Headers headers, String body) {
        this.startLine = startLine;
        this.headers = headers;
        this.body = body;
    }

    public StartLine getStartLine() {
        return startLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
