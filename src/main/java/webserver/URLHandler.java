package webserver;

import model.User;
import util.RequestParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class URLHandler {

    public User createUser(String URL) throws UnsupportedEncodingException {
        if (URL.startsWith("/user/create?")) {
            Map<String, String> form = RequestParser.parseURL(URL);
            return new User(form.get("userId"), form.get("password"),
                    form.get("name"), form.get("email"));
        }
        return null;
    }
}
