package webserver;

import http.RequestMaker;
import http.ResponseMaker;
import model.Request;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MainService;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final ResponseMaker responseMaker = new ResponseMaker();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestMaker requestMaker = new RequestMaker(in);
            Request request = requestMaker.make();
            MainService mainService = new MainService(request);
            Response response = mainService.serve();
            DataOutputStream dos = new DataOutputStream(out);
            dos.write(response.toBytes());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
