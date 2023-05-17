package http;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpBody {
    private String body;
    private Map<String, String> data = Maps.newHashMap();

    public HttpBody(String body) {
        this.body = body;
    }
}
