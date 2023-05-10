package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.MyModel;
import model.User;
import webserver.request.MyHttpRequest;
import webserver.response.MyHttpResponse;

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
            MyHttpRequest myHttpRequest = new MyHttpRequest(in);
            logger.debug("<< HTTP Request Message >>\n{}", myHttpRequest);

            if(myHttpRequest.getQueryParams().size() > 0) {
                MyModel myModel = new MyModel(myHttpRequest.getQueryParams());
                User user = new User(myModel.get("userId"), myModel.get("password"), myModel.get("name"), myModel.get("email"));
                logger.debug("User : {}", user);
            }

            DataOutputStream dos = new DataOutputStream(out);
            MyHttpResponse myHttpResponse = new MyHttpResponse(myHttpRequest);
            response200Header(dos, myHttpResponse);
            responseBody(dos, myHttpResponse);
        } catch (IOException e) {
            logger.error("읽을 수 없음: {}", e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, MyHttpResponse myHttpResponse) {
        try {
            dos.writeBytes(myHttpResponse.responseHeader());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, MyHttpResponse myHttpResponse) {
        try {
            dos.write(myHttpResponse.getBody(), 0, myHttpResponse.getContentLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
