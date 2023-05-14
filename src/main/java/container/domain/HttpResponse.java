package container.domain;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";
    private static final String CONTENT_LENGTH = "Content-Length: ";
    private static final String DELIMITER = " ";
    private static final String NEXT_LINE = "\r\n";
    private final HttpMethod httpMethod;
    private final String httpVersion;
    private final byte[] body;

    public HttpResponse(HttpMethod httpMethod, byte[] body) {
        this.httpMethod = httpMethod;
        this.body = body;
        this.httpVersion = DEFAULT_HTTP_VERSION;
    }

    public void written(DataOutputStream out) throws IOException {
        out.writeBytes(getHttpMethodHeader());
        out.writeBytes(CONTENT_LENGTH + body.length + NEXT_LINE);
        out.writeBytes(NEXT_LINE);
        out.write(body, 0, body.length);
    }

    private String getHttpMethodHeader() {
        return httpVersion + DELIMITER + httpMethod.getValue() + DELIMITER + httpMethod.name() + DELIMITER +
                NEXT_LINE;
    }
}
