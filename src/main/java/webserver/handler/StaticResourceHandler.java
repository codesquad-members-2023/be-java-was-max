package webserver.handler;

import static http.common.ContentType.of;
import static http.common.HttpStatus.OK;
import static http.common.HttpVersion.HTTP_1_1;

import http.common.ContentType;
import http.response.HttpServletResponse;
import http.response.StatusLine;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceHandler.class);

    public void process(File file, final HttpServletResponse response) throws IOException {
        byte[] messageBody = Files.readAllBytes(file.toPath());
        ContentType contentType = of(file.getPath());
        response.setStatusLine(new StatusLine(HTTP_1_1, OK));
        response.addHeader("Content-Type", contentType);
        response.addHeader("Content-Length", String.valueOf(messageBody.length));
        response.setMessageBody(messageBody);
    }
}
