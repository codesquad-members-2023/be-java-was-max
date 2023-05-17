package webserver;

import db.Database;
import model.Request;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class RequestExecuter {

    private final Logger logger = LoggerFactory.getLogger(RequestExecuter.class);

    public void execute(Request request) throws UnsupportedEncodingException {
        if (request.getRequestLine().get("Method").equals("POST") && request.getRequestLine().get("URL").equals("/user/create")) {
            Map<String, String> form = RequestParser.parseBody(request.getBody());
            User user = new User(form.get("userId"), form.get("password"),
                    form.get("name"), form.get("email"));
            Database.addUser(user);
            logger.debug("User added");
        }
    }

    public User createUser(String URL) throws UnsupportedEncodingException {
        if (URL.startsWith("/user/create?")) {
            Map<String, String> form = RequestParser.parseURL(URL);
            return new User(form.get("userId"), form.get("password"),
                    form.get("name"), form.get("email"));
        }
        return null;
    }
}
