package service;

import http.ResponseMaker;
import model.Request;
import model.Response;
import util.RequestParser;

import java.io.IOException;

public class MainService {

    private final Service service;
    private final Request request;
    private final ResponseMaker responseMaker;

    public MainService(Request request) {
        this.request = request;
        this.responseMaker = new ResponseMaker();
        String url = request.getRequestLine().getUrl();
        String[] parsedUrl = RequestParser.parseUrl(url);

        switch (parsedUrl[1]) {
            case "user":
                service = new UserService(request);
                return;
        }
        service = null;
    }

    public Response serve() throws IOException {
        if (request.getRequestLine().getMethod().isGet()) {
            return responseMaker.make(200, request.getRequestLine().getUrl());
        }
        if (service != null) {
            return service.serve();
        }
        return null;
    }
}
