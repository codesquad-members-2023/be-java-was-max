package webserver.dispatcher_servlet;

import cafe.app.user.controller.UserController;
import cafe.app.user.repository.MemoryUserRepository;
import cafe.app.user.repository.UserRepository;
import cafe.app.user.service.UserService;
import cafe.app.user.validator.UserValidator;

public class UserAppConfig {

    public UserController userController() {
        return new UserController(userService());
    }

    public UserService userService() {
        return new UserService(userRepository(), userValidator());
    }

    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }

    public UserValidator userValidator() {
        return new UserValidator();
    }
}
