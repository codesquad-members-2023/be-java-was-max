package servlet;

import db.Database;
import http.request.HttpMethod;
import http.request.HttpRequest;
import model.User;
import util.HttpRequestUtils;

public class UserServlet {
    public String findViewPath(HttpRequest httpRequest) {
        String method = httpRequest.getMethod();

        if (HttpMethod.get(method) == HttpMethod.GET) { // Query String 을 찾는다.
                Database.addUser(createUser(httpRequest.getQueryString().get()));
            }
        }
        if (HttpMethod.get(method) == HttpMethod.POST) { // Request Body를 찾는다.
            Database.addUser(createUser(httpRequest.getRequestBody().toString()));

        }
        return "/index.html"; // 유저 생성하고 home으로 redirect
    }

    private User createUser(String userData){
        HttpRequestUtils utils = new HttpRequestUtils();
        return utils.createUser(utils.parseQueryString(userData));
    }
}
