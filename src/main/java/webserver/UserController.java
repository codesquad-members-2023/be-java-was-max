package webserver;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserController {
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public void join(Map<String, String> queryMap) {
        User user = new User(queryMap.get("userId"), queryMap.get("password"), queryMap.get("name"), queryMap.get("email"));
        Database.addUser(user);
        logger.info(user.toString());
    }
}
