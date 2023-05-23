package servlet.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";
    private static final String CONTENT_LENGTH = "Content-Length: ";
    private static final String DELIMITER = " ";
    private static final String NEXT_LINE = "\r\n";
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final HttpResponseStatus httpResponseStatus;
    private final String httpVersion;
    private final String body;
    private String session;

    public HttpResponse(HttpResponseStatus httpResponseStatus, String body) {
        this.httpResponseStatus = httpResponseStatus;
        this.body = body;
        this.httpVersion = DEFAULT_HTTP_VERSION;
    }

    public HttpResponse(HttpResponseStatus httpResponseStatus, String body, String session) {
        this.httpResponseStatus = httpResponseStatus;
        this.body = body;
        this.httpVersion = DEFAULT_HTTP_VERSION;
        this.session = session;
    }

    public void written(DataOutputStream out) throws IOException {
        if (session != null) {
            String sessionReturn = getSessionReturn();
            out.writeBytes(sessionReturn);
            String s = "Set-Cookie: " + session + "; Path=/";
            logger.debug("session = {}", s);
            out.writeBytes(s+NEXT_LINE);
        } else {
            out.writeBytes(getResponseStartLine());
        }
        byte[] bytes = body.getBytes();
        out.writeBytes(CONTENT_LENGTH + bytes.length + NEXT_LINE);
        out.writeBytes(NEXT_LINE);
        out.write(bytes, 0, bytes.length);
    }

    private String getSessionReturn() {
        return httpVersion + DELIMITER + httpResponseStatus.getValue() + DELIMITER + httpResponseStatus.name()+NEXT_LINE;
    }

    private String getResponseStartLine() {
        return httpVersion + DELIMITER + httpResponseStatus.getValue() + DELIMITER + httpResponseStatus.name() + DELIMITER +
                NEXT_LINE;
    }
}
