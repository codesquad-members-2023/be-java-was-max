package webserver.util;

import model.ContentType;
import model.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseUtils {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private HttpResponseUtils() {
    }

    public static void responseBody(DataOutputStream dos, byte[] body, RequestLine requestLine) {
        try {
            dos.writeBytes(response200Header(requestLine.getContentType(), body.length));
            dos.write(body, 0, body.length);
            dos.flush();

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static String response200Header(ContentType contentType, int lengthOfBodyContent) {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + contentType.getMimeType() + "\r\n" +
                "Content-Length: " + lengthOfBodyContent + "\r\n" +
                "\r\n";
    }
}
