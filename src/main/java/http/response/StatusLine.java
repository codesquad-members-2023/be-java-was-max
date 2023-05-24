package http.response;

public class StatusLine {
    private final String httpVersion;  // HTTP/1.1
    private final String statusCode; // 200, 302, 404
    private final String reasonPhase; // OK, Found, Not Found

    public StatusLine(String statusLine) {
        String[] split = statusLine.split(" ");
        this.httpVersion = split[0];
        this.statusCode = split[1];
        this.reasonPhase = split[2];
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getReasonPhase() {
        return reasonPhase;
    }

    public String get(){
        return httpVersion + " " + statusCode + " " + reasonPhase;
    }
}
