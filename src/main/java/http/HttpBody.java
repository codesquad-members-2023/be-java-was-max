package http;

import java.util.Map;

public class HttpBody {
    private final Map<String, String> data;

    public HttpBody(String body) {
        this.data = HttpUtils.parseBody(body);
    }

    public String findValueByKey(String key) {
        return data.get(key);
    }

}
