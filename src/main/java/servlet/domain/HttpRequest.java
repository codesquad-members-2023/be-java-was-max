package servlet.domain;


import java.util.Map;

public class HttpRequest {
    private final StartLine startLine;

    private final Headers headers;

    private String body;


    private HttpRequest(StartLine startLine, Headers headers, String body) {
        this.startLine = startLine;
        this.headers = headers;
        this.body = body;
    }

    private HttpRequest(StartLine startLine, Headers headers) {
        this.startLine = startLine;
        this.headers = headers;
    }

    public static HttpRequest of(StartLine startLine, Headers headers) {
        return new HttpRequest(startLine, headers);
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

    public String getUrl() {
        return startLine.getUrl();
    }

    public Map<String, String> getParameters() {
        return startLine.getParameters();
    }
}
