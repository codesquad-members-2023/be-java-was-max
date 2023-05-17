package http;

import java.util.List;
import java.util.Map;

public class HttpHeader {
    private final Map<String, String> headers;

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
