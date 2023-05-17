package webserver;

import db.Database;
import model.User;

public class UserController {

    public void join(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }
}
