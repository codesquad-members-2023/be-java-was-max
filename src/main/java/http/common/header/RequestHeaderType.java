package http.common.header;

import java.util.Arrays;
import java.util.Optional;

public enum RequestHeaderType implements HeaderType {
    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    AUTHORIZATION("Authorization"),
    COOKIE("Cookie"),
    EXPECT("Expect"),
    FROM("From"),
    HOST("Host"),
    IF_MATCH("If-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    MAX_FORWARDS("Max-Forwards"),
    PROXY_AUTHORIZATION("Proxy-Authorization"),
    RANGE("Range"),
    REFERER("Referer"),
    TE("TE"),
    USER_AGENT("User-Agent");

    private final String value;

    RequestHeaderType(String value) {
        this.value = value;
    }

    public static Optional<RequestHeaderType> resolve(String headerName) {
        return Arrays.stream(values())
            .filter(headerType -> headerType.value()
                .equalsIgnoreCase(headerName))
            .findAny();
    }

    @Override
    public String value() {
        return value;
    }
}
