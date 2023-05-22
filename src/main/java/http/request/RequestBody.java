package http.request;

import http.HttpUtil;

import java.util.Map;

public class RequestBody {
    private final Map<String, String> data;

    public RequestBody(String body) {
        this.data = HttpUtil.parseBody(body);
    }

    public String findValueByKey(String key) {
        return data.get(key);
    }

}
