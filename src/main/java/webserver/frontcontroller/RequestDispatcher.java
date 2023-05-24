package webserver.frontcontroller;

import static http.common.HttpStatus.FOUND;
import static http.common.HttpStatus.OK;
import static http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static http.common.header.EntityHeaderType.CONTENT_TYPE;
import static http.common.header.ResponseHeaderType.LOCATION;
import static http.common.version.HttpVersion.HTTP_1_1;
import static http.response.component.ContentType.resolve;

import http.response.HttpResponse;
import http.response.component.ContentType;
import http.response.component.StatusLine;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RequestDispatcher {

    private final File file;

    public RequestDispatcher(File file) {
        this.file = file;
    }

    public void forward(HttpResponse response) {
        try {
            byte[] messageBody = Files.readAllBytes(file.toPath());
            ContentType contentType = resolve(file.getPath());
            response.setStatusLine(new StatusLine(HTTP_1_1, OK));
            response.addHeader(CONTENT_TYPE, contentType.toString());
            response.addHeader(CONTENT_LENGTH, String.valueOf(messageBody.length));
            response.setMessageBody(messageBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void redirect(HttpResponse response) {
        String path = file.getPath();
        response.addHeader(LOCATION, path);
        response.setStatusLine(new StatusLine(HTTP_1_1, FOUND));
    }
}
