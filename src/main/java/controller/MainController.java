package controller;

import annotation.Controller;
import annotation.RequestMapping;
import db.Database;
import db.PostDatabase;
import db.SessionDB;
import model.Post;
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
        User user = new User(userId, password, name, email);
        Database.addUser(user);
        UUID uuid = SessionDB.put(user);
        return "/user/list.html?sid=" + uuid;
    }

    @RequestMapping("/user/login.html")
    public String loginForm() {
        return "/user/login.html";
    }

    @RequestMapping(value = "/user/login", method = "POST")
    public String login(String password, String email) {
        User user = Database.findUserByEmail(email);
        if (user.getPassword().equals(password)) {
            UUID uuid = SessionDB.put(user);
            return "/index.html?sid=" + uuid;
        }
        return "/user/login_failed.html";
    }

    @RequestMapping(value = "/user/list.html")
    public String userList() {
        return "/user/list.html";
    }

    @RequestMapping(value = "/posts/form")
    public String qnaForm() {
        return "/qna/form.html";
    }

    @RequestMapping(value = "/posts", method = "POST")
    public String addPost(String nickname, String title, String content) {
        Post post = new Post(title, nickname, content);
        PostDatabase.addPost(post);
        return "/index.html";
    }

    @RequestMapping(value = "/posts/{postId}")
    public String showPost(int postId) {
        return "/qna/show.html?postId=" + postId;
    }
}
