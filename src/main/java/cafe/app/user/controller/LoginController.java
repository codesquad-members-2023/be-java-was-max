package cafe.app.user.controller;


import cafe.app.user.controller.dto.UserLoginRequest;
import cafe.app.user.controller.dto.UserResponse;
import cafe.app.user.entity.User;
import cafe.app.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.frontcontroller.Model;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionContainer;

import static webserver.http.common.HttpMethod.GET;
import static webserver.http.common.HttpMethod.POST;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 페이지
    @RequestMapping(path = "/login", method = GET)
    public String loginForm(HttpRequest request, HttpResponse response, Model model) {
        return "user/login";
    }

    // 로그인
    @RequestMapping(path = "/login", method = POST)
    public String login(HttpRequest request, HttpResponse response, Model model) {
        HttpSession httpSession = request.getHttpSession();
        String userId = request.getMessageBody().get("userId");
        String password = request.getMessageBody().get("password");
        UserLoginRequest requestDto = new UserLoginRequest(userId, password);
        try {
            User user = userService.login(requestDto);
            UserResponse userResponse = new UserResponse(user);
            httpSession.setAttribute("user", userResponse);
        } catch (Exception e) {
            return "redirect:user/login_failed";
        }
        return "redirect:/";
    }

    // 로그아웃
    @RequestMapping(path = "/logout", method = GET)
    public String logout(HttpRequest request, HttpResponse response, Model model) {
        SessionContainer.removeSession(request.getSessionId());
        return "redirect:/login";
    }
}
