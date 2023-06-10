package contorller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.request.HttpRequest;
import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public String mapper(HttpRequest httpRequest) {
        if(httpRequest.getUrl().contains("create")) {
            return signUp(httpRequest);
        } else if (httpRequest.getUrl().equals("/user/login")) {
            return signIn(httpRequest);
        }
        return httpRequest.getUrl();
    }

    public String signUp(HttpRequest httpRequest){
        Map<String,String> queryParam = httpRequest.getHeaders().getBodyParam();
        User user = new User(queryParam.get("userId"),queryParam.get("password"),queryParam.get("name"),queryParam.get("email"));
        logger.debug("user: {}", user);
        Database.addUser(user);
        return "redirect:/index.html";
    }

    public String signIn(HttpRequest httpRequest) {
        Map<String,String> bodyParam = httpRequest.getHeaders().getBodyParam();
        String id = bodyParam.get("userId");
        String password = bodyParam.get("password");
        User user = Database.findUserById(id);
        if(user == null) {
            return "redirect:/user/login_failed.html";
        }
        if(user.checkPassword(password)) {
            httpRequest.Session().setSession(user);
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }
}
