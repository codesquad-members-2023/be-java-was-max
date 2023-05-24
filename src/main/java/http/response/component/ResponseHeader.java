package http.response.component;

import http.common.header.HeaderType;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseHeader {

    private final Map<HeaderType, String> header;

    public ResponseHeader(Map<HeaderType, String> header) {
        this.header = header;
    }

    public Optional<String> get(HeaderType key) {
        return Optional.ofNullable(header.get(key));
    }

    public void put(HeaderType key, String value) {
        header.put(key, value);
    }

    public Set<HeaderType> keySet() {
        return header.keySet();
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
