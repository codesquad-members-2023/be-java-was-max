package webserver.frontcontroller;

import http.common.ContentType;
import http.response.HttpResponse;
import http.response.component.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.TemplateEngineParser;

import java.io.File;

import static http.common.ContentType.resolve;
import static http.common.HttpStatus.FOUND;
import static http.common.HttpStatus.OK;
import static http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static http.common.header.EntityHeaderType.CONTENT_TYPE;
import static http.common.header.ResponseHeaderType.LOCATION;
import static http.common.version.HttpVersion.HTTP_1_1;

public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);


    private final File file;
    private final String viewPath;

    public RequestDispatcher(File file, String viewPath) {
        this.file = file;
        this.viewPath = viewPath;
    }

    public void forward(HttpResponse response, ModelAndView modelAndView) {
        TemplateEngineParser engine = TemplateEngineParser.getInstance();
        byte[] messageBodyBytes = engine.parseHtmlDynamically(file.toPath(), modelAndView);

        ContentType contentType = resolve(file.getPath());
        response.setStatusLine(new StatusLine(HTTP_1_1, OK));
        response.addHeader(CONTENT_TYPE, contentType.toString());
        response.addHeader(CONTENT_LENGTH, String.valueOf(messageBodyBytes.length));
        response.setMessageBody(messageBodyBytes);
    }

    public void redirect(HttpResponse response) {
        response.addHeader(LOCATION, viewPath);
        response.setStatusLine(new StatusLine(HTTP_1_1, FOUND));
    }
}
