package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import model.Request;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final RequestExecuter requestExecuter = new RequestExecuter();
    private final RequestParser requestParser = new RequestParser();
    private final Responsor responsor = new Responsor();
    private final Request request = new Request();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = requestParser.readNParseRequest(in);
            DataOutputStream dos = new DataOutputStream(out);
            byte[] response = responsor.makeResponse(request.getRequestLine().get("URL"));
            dos.write(response);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
