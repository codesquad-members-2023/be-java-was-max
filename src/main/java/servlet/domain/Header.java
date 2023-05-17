package servlet.domain;

import java.util.Map.Entry;

public class Header {
    private final String name;
    private final String value;

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static Header from(Entry<String, String> entry) {
        return new Header(entry.getKey(), entry.getValue());
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
