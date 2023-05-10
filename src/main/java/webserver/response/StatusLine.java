package webserver.response;

public class StatusLine {

    private String httpVersion;
    private int statusCode;
    private String reasonPhrase;

    public StatusLine(String httpVersion, int statusCode, String reasonPhrase) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        return String.format("%s %d %s", httpVersion, statusCode, reasonPhrase);
    }
}
