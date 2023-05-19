package http.session;

import java.util.ArrayList;
import java.util.List;

public class Cookie {

    private final String name;
    private final String value;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static List<Cookie> parse(String cookieString) {
        int key_index = 0;
        int value_index = 1;

        // 쿠키가 없는 경우
        if (cookieString == null) {
            return new ArrayList<>();
        }

        String[] tokens = cookieString.split(";");
        List<Cookie> cookies = new ArrayList<>();

        for (String token : tokens) {
            String key = token.split("=")[key_index];
            String value = token.split("=")[value_index];
            cookies.add(new Cookie(key, value));
        }
        return cookies;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s=%s", name, value);
    }
}
