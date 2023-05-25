package servlet;

import db.Database;
import http.request.HttpMethod;
import http.request.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;



public class UserCreateServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateServlet.class);
    public String findViewPath(HttpRequest httpRequest) {
        String method = httpRequest.getMethod();

        if (HttpMethod.get(method) == HttpMethod.GET) { // Query String 을 찾는다.
            if (httpRequest.getQueryString().isPresent()) { // Query String이 비어있지 않으면 user 객체 생성
                String userId = Database.addUser(createUser(httpRequest.getQueryString().get()));
                logger.debug("User: {}", Database.findUserById(userId));
            }
        }
        if (HttpMethod.get(method) == HttpMethod.POST) { // Request Body를 찾는다.
            String userId = Database.addUser(createUser(httpRequest.getRequestBody()));
            logger.debug("User: {}", Database.findUserById(userId));
        }
        return "redirect:/user/login.html"; // 유저 생성하고 login.html으로 redirect
    }

    private User createUser(String userData){
        HttpRequestUtils utils = new HttpRequestUtils();
        return utils.createUser(utils.parseQueryString(userData));
    }

}
