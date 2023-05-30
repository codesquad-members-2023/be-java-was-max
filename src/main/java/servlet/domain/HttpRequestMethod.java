package servlet.domain;

import java.util.Arrays;

public enum HttpRequestMethod {
    GET, HEAD, OPTIONS, PATH, POST, PUT, TRACE, CONNECT;

    public static HttpRequestMethod from(String name) {
        return Arrays.stream(values())
                .filter(httpRequestMethod -> httpRequestMethod.name().equals(name))
                .findFirst().orElseThrow();
    }

    public boolean isSameName(String httpRequestMethod) {
        return this.name().equals(httpRequestMethod);
    }
}
