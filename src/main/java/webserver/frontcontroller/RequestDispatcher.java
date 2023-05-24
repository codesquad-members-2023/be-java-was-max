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
    private final String viewPath;

    public RequestDispatcher(File file, String viewPath) {
        this.file = file;
        this.viewPath = viewPath;
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
        response.addHeader(LOCATION, viewPath);
        response.setStatusLine(new StatusLine(HTTP_1_1, FOUND));
    }
}
