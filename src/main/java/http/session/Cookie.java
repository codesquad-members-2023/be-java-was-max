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
        final int KEY_INDEX = 0;
        final int VALUE_INDEX = 1;
        final String COOKIE_SEPARATOR = ";\\s*";
        final String KEY_VALUE_SEPARATOR = "=";

        // 쿠키가 없는 경우
        if (cookieString == null) {
            return new ArrayList<>();
        }

        String[] tokens = cookieString.split(COOKIE_SEPARATOR);
        List<Cookie> cookies = new ArrayList<>();

        for (String token : tokens) {
            String key = token.split(KEY_VALUE_SEPARATOR)[KEY_INDEX];
            String value = token.split(KEY_VALUE_SEPARATOR)[VALUE_INDEX];
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
