package servlet.domain;

import java.util.List;

public class RequestHeaders {
    private final List<Header> headers;

    public RequestHeaders(List<Header> headers) {
        this.headers = headers;
    }
}
