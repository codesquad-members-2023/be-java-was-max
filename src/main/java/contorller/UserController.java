package contorller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.request.HttpRequest;

import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public String mapper(HttpRequest httpRequest) {
        Map<String,String> queryParam = httpRequest.getHeaders().getBodyParam();
        if(httpRequest.getUrl().contains("create")) {
            return signUp(queryParam);
        }
        return httpRequest.getUrl();
    }

    public String signUp(Map<String,String> queryParam){
        User user = new User(queryParam.get("userId"),queryParam.get("password"),queryParam.get("name"),queryParam.get("email"));
        logger.debug("user: {}", user);
        return "redirect:/index.html";
    }
}
