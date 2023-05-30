package servlet.domain;

import java.util.Map.Entry;

public class Header {
    private final String key;
    private final String value;

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Header from(Entry<String, String> entry) {
        return new Header(entry.getKey(), entry.getValue());
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


}
