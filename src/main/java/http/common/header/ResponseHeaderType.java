package http.common.header;

import java.util.Arrays;
import java.util.Optional;

public enum ResponseHeaderType implements HeaderType {
    ACCEPT_RANGES("Accept-Ranges"),
    AGE("Age"),
    ETAG("ETag"),
    LOCATION("Location"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"),
    RETRY_AFTER("Retry-After"),
    SERVER("Server"),
    VARY("Vary"),
    WWW_AUTHENTICATE("WWW-Authenticate");

    private String value;

    ResponseHeaderType(String value) {
        this.value = value;
    }


    public static Optional<ResponseHeaderType> resolve(String headerName) {
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
