package servlet.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class RequestHeaders {
    private final List<Header> headers;

    public RequestHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public static RequestHeaders from(Map<String, String> parseHeaders) {
        return new RequestHeaders(parseHeaders.entrySet().stream()
                .map(Header::from)
                .collect(Collectors.toList()));
    }

    public int getContentLength() {
        return headers.stream()
                .filter(header -> Objects.equals(header.getKey(), "Content-Length"))
                .map(header -> Integer.parseInt(header.getValue()))
                .findFirst().orElse(0);
    }
}
