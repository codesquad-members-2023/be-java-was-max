package servlet.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Headers {
    private final List<Header> headers;

    public Headers(List<Header> headers) {
        this.headers = headers;
    }

    public static Headers of(Map<String, String> parseHeaders) {
        return new Headers(parseHeaders.entrySet().stream()
                .map(Header::from)
                .collect(Collectors.toList()));
    }

    public List<Header> getHeaders() {
        return headers;
    }
}
