package util.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);


    public void response(OutputStream out, String url) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        String contentValue = url.substring(url.lastIndexOf(".") + 1);
        String contentType = ContentType.equalsValue(contentValue);
        logger.debug("type : {} ", contentType);
        String path = "src/main/resources/templates";
        if (!contentValue.equals("html")) {
            path = "src/main/resources/static";
        }
        if (url.split(":")[0].equals("redirect")) {
            url = url.split(":")[1];
            response302Header(dos, url);
        } else {
            byte[] body = Files.readAllBytes(new File(path + url).toPath());
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(
                    "Content-Type: " + contentType + (contentType.contains("text") ? ";charset=utf-8" : "") + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        String redirectUrl[] = url.split(":");
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND\r\n");
            dos.writeBytes("Location: " + redirectUrl[0] + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
