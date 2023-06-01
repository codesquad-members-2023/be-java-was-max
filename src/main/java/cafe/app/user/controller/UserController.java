package cafe.app.user.controller;


import cafe.app.user.controller.dto.UserSavedRequest;
import cafe.app.user.service.UserService;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.frontcontroller.Model;
import webserver.http.request.HttpRequest;
import webserver.http.request.component.RequestMessageBody;
import webserver.http.response.HttpResponse;

import static webserver.http.common.HttpMethod.GET;
import static webserver.http.common.HttpMethod.POST;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/new", method = GET)
    public String addUserForm(HttpRequest request, HttpResponse response, Model model) {
        return "user/form";
    }

    @RequestMapping(path = "/users", method = GET)
    public String listUser(HttpRequest request, HttpResponse response, Model model) {
        // 로그인 하지 않은 경우 로그인 페이지로 이동
        if (!request.hasHttpSession()) {
            return "redirect:user/login";
        }
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @RequestMapping(path = "/users", method = POST)
    public String createUser(HttpRequest request, HttpResponse response, Model model) {
        RequestMessageBody messageBody = request.getMessageBody();
        String userId = messageBody.get("userId");
        String password = messageBody.get("password");
        String name = messageBody.get("name");
        String email = messageBody.get("email");
        UserSavedRequest userRequest = new UserSavedRequest(userId, password, name, email);
        userService.signUp(userRequest);
        return "redirect:/login";
    }
}
