package servlet.domain;

import java.util.Map;

public class HttpBody {

    private final Map<String, String> keyValue;

    public HttpBody(Map<String, String> keyValue) {
        this.keyValue = keyValue;
    }

    public static HttpBody from(Map<String, String> parseBody) {
        return new HttpBody(parseBody);
    }

    public Map<String, String> getKeyValue() {
        return keyValue;
    }

    public boolean isSameCount(int count) {
        return keyValue.size() == count;
    }
}
