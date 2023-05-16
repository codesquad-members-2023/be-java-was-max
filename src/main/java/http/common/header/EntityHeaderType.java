package http.common.header;

import java.util.Arrays;
import java.util.Optional;

public enum EntityHeaderType implements HeaderType {
    ALLOW("Allow"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_LOCATION("Content-Location"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    EXPIRES("Expires"),
    Last_Modified("Last-Modified"),
    EXTENSION_HEADER("extension-header");

    private String value;

    EntityHeaderType(String value) {
        this.value = value;
    }

    public static Optional<EntityHeaderType> resolve(String headerName) {
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
