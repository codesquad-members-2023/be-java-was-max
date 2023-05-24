package webserver;

import java.io.*;
import java.net.Socket;

import http.RequestMaker;
import http.Responsor;
import model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MainService;
import util.RequestParser;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Responsor responsor = new Responsor();
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
            mainService.serve();
            DataOutputStream dos = new DataOutputStream(out);
            byte[] response = responsor.makeResponse(request.getRequestLine().getUrl());
            dos.write(response);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
