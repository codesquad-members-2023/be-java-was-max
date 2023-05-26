package servlet;

import auth.Session;
import db.Database;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;

public class UserLoginServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginServlet.class);

    public String findViewPath(HttpRequest httpRequest, HttpResponse httpResponse) {
        String method = httpRequest.getMethod();
        String path = "/user/login.html";  // Method가 Get이면 path는 이대로 유지

        if (HttpMethod.get(method) == HttpMethod.POST) { // Request Body를 찾는다.
            // 유저 찾기 성공 실패에 따라 return 되는 path가 다르다.
            path = login(httpRequest.getRequestBody(), httpResponse);
        }
        return path;
    }

    private String login(String userData, HttpResponse httpResponse) {
        HttpRequestUtils utils = new HttpRequestUtils();
        Map<String, String> userInfo = utils.parseQueryString(userData);
        String userId = userInfo.get("userId");
        String password = userInfo.get("password");

        if (Database.findUserById(userId).isEmpty()) { // 아이디가 다르면
            return "/user/login_failed.html";
        }

        User user = Database.findUserById(userId).get();
        if (!password.equals(user.getPassword())) { //  비밀번호가 다르면
            return "/user/login_failed.html";
        }

        Session.createSessionId(user, httpResponse);
        return "redirect:/index.html";
    }
}

