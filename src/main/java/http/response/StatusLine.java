package http.response;

public class StatusLine {

    private final String protocol;
    private final String status;
    private final String reasonPhrase;

    public StatusLine(final String protocol, final String status, final String reasonPhrase) {
        this.protocol = protocol;
        this.status = status;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public String toString() {
        return "HttpResponse [protocol=" + protocol + ", status=" + status + ", reasonPhrase=" + reasonPhrase + "]";
    }
}
