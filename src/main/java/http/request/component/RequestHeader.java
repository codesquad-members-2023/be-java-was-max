package http.request.component;

import java.util.Map;
import java.util.stream.Collectors;

public class RequestHeader {

    private final Map<String, String> header;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public boolean containsKey(String key) {
        return header.containsKey(key);
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    @Override
    public String toString() {
        return header.keySet()
            .stream()
            .map(key -> String.format("%s: %s", key, header.get(key)))
            .collect(Collectors.joining("\r\n"))
            .trim();
    }
}
