package webserver.handler;

import static http.parser.HttpRequestParser.parseHttpRequest;
import static util.FileUtils.getFile;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.dispatcher_servlet.DispatcherServlet;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest request = parseHttpRequest(br);
            HttpResponse response = new HttpResponse();
            StaticResourceHandler staticResourceHandler = new StaticResourceHandler();
            logger.debug("httpRequest : {}", request);

            Optional<File> optionalStaticResource = getFile(request.getPath());

            if (optionalStaticResource.isPresent()) {
                File file = optionalStaticResource.get();
                staticResourceHandler.process(file, request, response);
            } else {
                DispatcherServlet dispatcherServlet = new DispatcherServlet();
                dispatcherServlet.doDispatch(request, response);
            }

            response200Header(dos, response);
            responseBody(dos, response);
            logger.debug("httpResponse : {}", response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void response200Header(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            byte[] messageBody = httpResponse.getMessageBody();
            dos.write(messageBody, 0, messageBody.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
