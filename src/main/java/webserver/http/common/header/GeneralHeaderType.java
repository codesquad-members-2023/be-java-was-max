package webserver.http.common.header;

import java.util.Arrays;
import java.util.Optional;

public enum GeneralHeaderType implements HeaderType {
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    DATE("Date"),
    PRAGMA("Pragma"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    UPGRADE("Upgrade"),
    VIA("Via"),
    WARNING("Warning");

    private String value;

    GeneralHeaderType(String value) {
        this.value = value;
    }

    public static Optional<GeneralHeaderType> resolve(String headerName) {
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
