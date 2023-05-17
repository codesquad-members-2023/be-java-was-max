package webserver;

import java.io.*;
import java.net.Socket;

import contorller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.request.HttpHeader;
import util.request.HttpRequest;
import util.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            HttpHeader headers = new HttpHeader(bufferedReader);
            HttpRequest request = new HttpRequest(headers);
            UserController userController = new UserController();
            HttpResponse response = new HttpResponse();
            response.response(out,userController.mapper(request));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
