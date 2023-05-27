package cafe.app.user.controller;


import static http.common.HttpMethod.GET;
import static http.common.HttpMethod.POST;

import annotation.Controller;
import annotation.RequestMapping;
import cafe.app.user.controller.dto.UserSavedRequest;
import cafe.app.user.service.UserService;
import http.request.HttpRequest;
import http.request.component.RequestMessageBody;
import http.response.HttpResponse;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/new", method = GET)
    public String addUserForm(HttpRequest request, HttpResponse response) {
        return "user/form";
    }

    @RequestMapping(path = "/users", method = POST)
    public String createUser(HttpRequest request, HttpResponse response) {
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
