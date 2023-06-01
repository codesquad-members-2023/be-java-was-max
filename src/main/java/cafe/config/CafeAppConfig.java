package cafe.config;

import cafe.app.user.controller.HomeController;
import cafe.app.user.controller.LoginController;
import cafe.app.user.controller.UserController;
import cafe.app.user.repository.MemoryUserRepository;
import cafe.app.user.repository.UserRepository;
import cafe.app.user.service.UserService;
import cafe.app.user.validator.UserValidator;

public class CafeAppConfig {

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

    public LoginController loginController() {
        return new LoginController(userService());
    }

    public HomeController homeController() {
        return new HomeController();
    }
}
