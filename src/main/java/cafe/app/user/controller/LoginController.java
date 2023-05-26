package cafe.app.user.controller;


import static http.common.HttpMethod.GET;
import static http.common.HttpMethod.POST;

import annotation.Controller;
import annotation.RequestMapping;
import cafe.app.user.controller.dto.UserLoginRequest;
import cafe.app.user.controller.dto.UserResponse;
import cafe.app.user.entity.User;
import cafe.app.user.service.UserService;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSession;
import http.session.SessionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 페이지
    @RequestMapping(path = "/login", method = GET)
    public String loginForm(HttpRequest request, HttpResponse response) {
        return "user/login";
    }

    // 로그인
    @RequestMapping(path = "/login", method = POST)
    public String login(HttpRequest request, HttpResponse response) {
        HttpSession httpSession = request.getHttpSession();
        String userId = request.getMessageBody()
            .get("userId");
        String password = request.getMessageBody()
            .get("password");
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
    public String logout(HttpRequest request, HttpResponse response) {
        SessionContainer.removeSession(request.getSessionId());
        return "redirect:/login";
    }
}
