package model;

public class StatusLine {

    private static final String PROTOCOL = "HTTP/1.1";
    private static final String CRLF = "\r\n";
    private final Status status;
    public StatusLine(int statusCode) {
        this.status = Status.getStatusOfCode(statusCode);
    }

    public byte[] toBytes() {
        return toString().getBytes();
    }

    @Override
    public String toString() {
        return PROTOCOL + " " + status.getStatusMessage() + CRLF;
    }
}
