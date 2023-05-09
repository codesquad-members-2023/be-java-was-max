package webserver;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private final HttpMethod httpMethod;
    private final byte[] body;
    private final int contentLength;

    public HttpResponse(HttpMethod httpMethod, byte[] body) {
        this.httpMethod = httpMethod;
        this.body = body;
        this.contentLength = body.length;
    }

    public void written(DataOutputStream out) throws IOException {
        out.writeBytes("HTTP/1.1 " + httpMethod.getValue() + " " + httpMethod.name() + " \r\n");
        out.writeBytes("Content-Length: " + contentLength + "\r\n");
        out.writeBytes("\r\n");
        out.write(body, 0, body.length);
    }
}
