package webserver.http.common.version;

public class HttpVersion extends ProtocolVersion {

    public static final String HTTP = "HTTP";
    public static final ProtocolVersion HTTP_0_9 = new ProtocolVersion("HTTP", 0, 9);
    public static final ProtocolVersion HTTP_1_0 = new ProtocolVersion("HTTP", 1, 0);
    public static final ProtocolVersion HTTP_1_1 = new ProtocolVersion("HTTP", 1, 1);

    public HttpVersion(int major, int minor) {
        super(HTTP, major, minor);
    }
}
