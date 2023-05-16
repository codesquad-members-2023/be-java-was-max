package http.response.component;

import http.common.header.HeaderType;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {

    private final Map<HeaderType, String> header;

    public ResponseHeader(Map<HeaderType, String> header) {
        this.header = header;
    }

    public boolean containsKey(HeaderType key) {
        return header.containsKey(key);
    }

    public String getHeaderValue(HeaderType key) {
        return header.get(key);
    }

    public void addHeader(HeaderType key, String value) {
        header.put(key, value);
    }

    @Override
    public String toString() {
        return header.keySet()
            .stream()
            .map(key -> String.format("%s: %s", key.value(), header.get(key)))
            .collect(Collectors.joining("\r\n"))
            .trim();
    }
}
