package servlet;

import db.Database;
import http.request.HttpMethod;
import http.request.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;



public class UserServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);
    public String findViewPath(HttpRequest httpRequest) {
        String method = httpRequest.getMethod();

        if (HttpMethod.get(method) == HttpMethod.GET) { // Query String 을 찾는다.
                Database.addUser(createUser(httpRequest.getQueryString().get()));
                String userId = Database.addUser(createUser(httpRequest.getQueryString().get()));
                logger.debug("User: {}", Database.findUserById(userId));
            }
        }
        if (HttpMethod.get(method) == HttpMethod.POST) { // Request Body를 찾는다.
            String userId = Database.addUser(createUser(httpRequest.getRequestBody()));
            logger.debug("User: {}", Database.findUserById(userId));
        }
        return "redirect:/index.html"; // 유저 생성하고 home으로 redirect
    }

    private User createUser(String userData){
        HttpRequestUtils utils = new HttpRequestUtils();
        return utils.createUser(utils.parseQueryString(userData));
    }

}
