package webserver.frontcontroller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.component.ContentType;
import http.response.component.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.jsp.JspCompiler;
import webserver.jsp.JspServlet;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

import static http.common.HttpStatus.FOUND;
import static http.common.HttpStatus.OK;
import static http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static http.common.header.EntityHeaderType.CONTENT_TYPE;
import static http.common.header.ResponseHeaderType.LOCATION;
import static http.common.version.HttpVersion.HTTP_1_1;
import static http.response.component.ContentType.resolve;
import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);


    private final File file;
    private final String viewPath;

    public RequestDispatcher(File file, String viewPath) {
        this.file = file;
        this.viewPath = viewPath;
    }

    public void forward(HttpRequest request, HttpResponse response) {
        try {
            JspServlet servlet = JspCompiler.service(file);
            servlet.service(request, response);

            response.getMessageBodyWriter().close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getMessageBodyOutputStream().toByteArray()), UTF_8));
            String messageBody = reader.lines().collect(Collectors.joining());
            byte[] messageBodyBytes = messageBody.getBytes(UTF_8);

            ContentType contentType = resolve(file.getPath());
            response.setStatusLine(new StatusLine(HTTP_1_1, OK));
            response.addHeader(CONTENT_TYPE, contentType.toString());
            response.addHeader(CONTENT_LENGTH, String.valueOf(messageBodyBytes.length));
            response.setMessageBody(messageBodyBytes);
        } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void redirect(HttpRequest request, HttpResponse response) {
        response.addHeader(LOCATION, viewPath);
        response.setStatusLine(new StatusLine(HTTP_1_1, FOUND));
    }
}
