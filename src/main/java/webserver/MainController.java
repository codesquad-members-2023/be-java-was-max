package webserver;

import annotation.Controller;
import annotation.RequestMapping;
import db.Database;
import model.User;

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

    @RequestMapping("/user/create")
    public String viewUserList(String userId, String password, String name, String email) {
        Database.addUser(new User(userId, password, name, email));
        return "/user/list.html";
    }
}
