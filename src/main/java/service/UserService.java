package service;

import db.Database;
import http.ResponseMaker;
import model.Request;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;

import java.io.IOException;
import java.util.Map;

public class UserService implements Service{

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final Request request;

    public UserService(Request request) {
        this.request = request;
    }

    @Override
    public Response serve() throws IOException {
        ResponseMaker responseMaker = new ResponseMaker();
        String[] parsedUrl = RequestParser.parseUrl(request.getRequestLine().getUrl());
        if (request.getRequestLine().hasBody() && parsedUrl[2].equals("create")) {
            addUser();
            return responseMaker.make(302, "/index.html");
        }
        return null;
    }

    private void addUser() {
        String body = request.getBody().getBody();
        Map<String, String> form = RequestParser.parseBody(body);
        User user = new User(form.get("userId"), form.get("password"), form.get("name"), form.get("email"));

        Database.addUser(user);
        logger.debug("{} is added", user.getUserId());
    }
}
