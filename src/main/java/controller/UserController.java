package controller;

import db.Database;
import model.User;

import java.util.Map;

public class UserController {
    public void join(Map<String, String> queryMap) {
        User user = new User(queryMap.get("userId"), queryMap.get("password"), queryMap.get("name"), queryMap.get("email"));
        Database.addUser(user);
    }
}
