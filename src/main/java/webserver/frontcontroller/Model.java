package webserver.frontcontroller;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, Object> store = new HashMap<>();

    public void addAttribute(String key, Object value) {
        store.put(key, value);
    }

    public Object getAttribute(String key) {
        return store.getOrDefault(key, null);
    }

    public boolean containsAttribute(String key) {
        return store.containsKey(key);
    }
}
