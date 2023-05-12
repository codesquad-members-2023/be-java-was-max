package cafe.app.user.controller;


import static http.common.HttpMethod.GET;

import annotation.Controller;
import annotation.RequestMapping;
import cafe.app.user.controller.dto.UserSavedRequest;
import cafe.app.user.service.UserService;
import http.request.HttpServletRequest;
import http.request.component.RequestParameter;
import http.response.HttpServletResponse;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/create", method = GET)
    public String createUser(HttpServletRequest request, HttpServletResponse response) {
        RequestParameter requestParameter = request.getParameter();
        String userId = requestParameter.get("userId");
        String password = requestParameter.get("password");
        String name = requestParameter.get("name");
        String email = requestParameter.get("email");
        UserSavedRequest userRequest = new UserSavedRequest(userId, password, name, email);
        userService.signUp(userRequest);
        return "redirect:user/login";
    }
}
