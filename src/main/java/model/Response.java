package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Response {

    private final StatusLine statusLine;
    private final ResponseHeaders headers;
    private final byte[] body;

    public Response(StatusLine statusLine, ResponseHeaders headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        out.write(statusLine.toBytes());
        out.write(headers.toBytes());
        if (body != null) {
            out.write(body);
        }
        return out.toByteArray();
    }

}
