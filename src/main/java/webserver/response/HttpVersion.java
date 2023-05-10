package webserver.response;

public class HttpVersion {

    private static final String name = "HTTP";

    private final double version;

    public HttpVersion(double version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return String.format("%s/%.1f", name, version);
    }
}
