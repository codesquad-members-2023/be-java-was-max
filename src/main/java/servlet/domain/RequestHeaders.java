package servlet.domain;

import java.util.List;
import java.util.Map;
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

}
