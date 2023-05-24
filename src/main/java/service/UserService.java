package service;

import db.Database;
import model.Request;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;

import java.util.Map;

public class UserService implements Service{

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final Request request;

    public UserService(Request request) {
        this.request = request;
    }

    @Override
    public void serve() {
        String[] parsedUrl = RequestParser.parseUrl(request.getRequestLine().getUrl());
        if (request.getRequestLine().hasBody() && parsedUrl[2].equals("create")) {
            addUser();
        }
    }

    private void addUser() {
        String body = request.getBody().getBody();
        Map<String, String> form = RequestParser.parseBody(body);
        User user = new User(form.get("userId"), form.get("password"), form.get("name"), form.get("email"));

        Database.addUser(user);
        logger.debug("{} is added", user.getUserId());
    }
}
