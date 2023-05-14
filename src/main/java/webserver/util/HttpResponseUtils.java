package webserver.util;

import model.Line;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponseUtils {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final Map<String, String> mimeTypes = Map.of("css", "text/css",
            "js", "application/javascript",
            "fonts", "application/octet-stream",
            "html", "text/html;charset=utf=8");

    private HttpResponseUtils() {
    }

    public static void responseBody(DataOutputStream dos, byte[] body, Line line) {
        try {
            dos.writeBytes(response200Header(line, body.length));
            dos.write(body, 0, body.length);
            dos.flush();

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static String response200Header(Line line, int lengthOfBodyContent) {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + mimeTypes.get(line.separateRequestType()) + "\r\n" +
                "Content-Length: " + lengthOfBodyContent + "\r\n" +
                "\r\n";
    }
}
