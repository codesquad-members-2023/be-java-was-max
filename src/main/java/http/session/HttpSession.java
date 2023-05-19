package http.session;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {

    private final ConcurrentHashMap<String, Object> attributes = new ConcurrentHashMap<>();

    private final String id;

    public HttpSession(String id) {
        this.id = id;
    }

    public static List<Cookie> createSessionIdCookie(String sid) {
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(new Cookie("sid", sid));
        cookies.add(new Cookie("Path", "/"));
        return cookies;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s %s", id, attributes);
    }
}
