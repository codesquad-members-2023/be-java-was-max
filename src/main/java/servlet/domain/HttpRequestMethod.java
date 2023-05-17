package servlet.domain;

import java.util.Arrays;

public enum HttpRequestMethod {
    GET, HEAD, OPTIONS, PATH, POST, PUT, TRACE, CONNECT;

    public static HttpRequestMethod parse(String name) {
        return Arrays.stream(values())
                .filter(httpRequestMethod -> httpRequestMethod.name().equals(name))
                .findFirst().orElseThrow();
    }
}
