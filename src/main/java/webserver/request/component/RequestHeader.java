package webserver.request.component;

import java.util.Map;
import java.util.stream.Collectors;

public class RequestHeader {

    private final Map<String, Object> header;

    public RequestHeader(Map<String, Object> header) {
        this.header = header;
    }

    public Object getHeader(String key) {
        return header.get(key);
    }

    public void addHeader(String key, Object value) {
        header.put(key, value);
    }

    @Override
    public String toString() {
        return header.keySet().stream()
            .map(key -> String.format("%s: %s", key, header.get(key)))
            .collect(Collectors.joining("\r\n")).trim();
    }
}
