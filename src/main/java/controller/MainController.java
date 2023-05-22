package controller;

import annotation.Controller;
import annotation.RequestMapping;
import db.Database;
import db.SessionDB;
import model.User;

import java.util.UUID;

@Controller
public class MainController {
    private static final MainController mainController = new MainController();

    private MainController() {
    }

    public static MainController getInInstance() {
        return mainController;
    }

    @RequestMapping("/")
    public String viewMain() {
        return "Hello World";
    }

    @RequestMapping("/index.html")
    public String viewIndex() {
        return "/index.html";
    }

    @RequestMapping("/user/form.html")
    public String viewUserForm() {
        return "/user/form.html";
    }

    @RequestMapping(value = "/user/create", method = "POST")
    public String viewUserList(String password, String name, String userId, String email) {
        Database.addUser(new User(userId, password, name, email));
        return "/user/list.html";
    }

    @RequestMapping("/user/login.html")
    public String loginForm() {
        return "/user/login.html";
    }

    @RequestMapping(value = "/user/login", method = "POST")
    public String login(String password, String email) {
        User user = Database.findUserByEmail(email);
        if (user.getPassword().equals(password)) {
            UUID uuid = UUID.randomUUID();
            SessionDB.put(uuid, user);
            return "/index.html?sid=" + uuid;
        }
        return "/user/login_failed.html";
    }
}
