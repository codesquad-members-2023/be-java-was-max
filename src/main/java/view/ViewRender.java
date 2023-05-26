package view;

import http.ContentType;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class ViewRender {
    private static final Logger logger = LoggerFactory.getLogger(ViewRender.class);
    private String REDIRECT;

    public void render(String viewPath, byte[] body, HttpResponse httpResponse, String redirect) {
        this.REDIRECT = redirect;
        setResponse(viewPath, httpResponse, body);
    }

    private void setResponse(String viewPath, HttpResponse httpResponse, byte[] body){
        httpResponse.setStatusLine(setStatusLine(viewPath)); // statusLine

        httpResponse.addResponseHeaders("Content-Type: " + ContentType.get(viewPath) + ";charset=utf-8\r\n"); // responseHeaders
        httpResponse.addResponseHeaders("Content-Length: " + body.length + "\r\n");
        if (httpResponse.getStatusCode().equals("302")) {  // redirection이면
            httpResponse.addResponseHeaders("Location: " + viewPath.replace(REDIRECT, "") + "\r\n");
        }

        httpResponse.setResponseBody(body); // responseBody
        httpResponse.logResponse(); // log
    }

    private String setStatusLine(String viewPath){
        if (viewPath.startsWith(REDIRECT)) {
            return "HTTP/1.1 302 Found \r\n";
        }
        return "HTTP/1.1 200 OK \r\n";
    }
}
