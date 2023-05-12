package webserver.handler;

import static util.FileUtils.getFile;

import http.request.HttpRequestFactory;
import http.request.HttpServletRequest;
import http.response.HttpServletResponse;
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
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            HttpServletRequest request = HttpRequestFactory.create(br);
            HttpServletResponse response = new HttpServletResponse();
            StaticResourceHandler staticResourceHandler = new StaticResourceHandler();

            Optional<File> optionalStaticResource = getFile(request.getPath());

            optionalStaticResource.ifPresentOrElse(file -> {
                try {
                    staticResourceHandler.process(file, response);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }, () -> {
                try {
                    DispatcherServlet dispatcherServlet = new DispatcherServlet();
                    dispatcherServlet.doDispatch(request, response);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            });

            response200Header(dos, response);
            responseBody(dos, response);
            logger.debug("httpRequest : {}", request);
            logger.debug("httpResponse : {}", response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void response200Header(DataOutputStream dos, HttpServletResponse httpServletResponse) {
        try {
            dos.writeBytes(httpServletResponse.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpServletResponse httpServletResponse) {
        try {
            byte[] messageBody = httpServletResponse.getMessageBody();
            dos.write(messageBody, 0, messageBody.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
