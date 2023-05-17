package http;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class HttpHeader {
    private Map<String, String> headers = Maps.newHashMap();

    public HttpHeader(List<String> headers) {
        this.headers = HttpUtils.parseHeader(headers);
    }

    public boolean contains(String header) {
        return headers.containsKey(header);
    }

    public String findFieldByName(String name) {
        return headers.get(name);
    }
}
