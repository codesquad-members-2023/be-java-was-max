package webserver.handler;

import static http.common.HttpStatus.OK;
import static http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static http.common.header.EntityHeaderType.CONTENT_TYPE;
import static http.common.version.HttpVersion.HTTP_1_1;
import static http.response.component.ContentType.resolve;

import http.response.HttpResponse;
import http.response.component.ContentType;
import http.response.component.StatusLine;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceHandler.class);

    public void process(File file, final HttpResponse response) throws IOException {
        byte[] messageBody = Files.readAllBytes(file.toPath());
        ContentType contentType = resolve(file.getPath());
        response.setStatusLine(new StatusLine(HTTP_1_1, OK));
        response.addHeader(CONTENT_TYPE, contentType.toString());
        response.addHeader(CONTENT_LENGTH, String.valueOf(messageBody.length));
        response.setMessageBody(messageBody);
    }
}
