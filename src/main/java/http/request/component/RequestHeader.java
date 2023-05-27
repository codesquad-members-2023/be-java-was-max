package http.request.component;

import http.common.header.HeaderType;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHeader {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private final Map<HeaderType, String> header;

    public RequestHeader(Map<HeaderType, String> header) {
        this.header = header;
    }

    public Optional<String> get(HeaderType key) {
        return Optional.ofNullable(header.get(key));
    }

    public String put(HeaderType key, String value) {
        return header.put(key, value);
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
