package servlet.domain;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0"),
    HTTP_3_0("HTTP/3.0");
    private final String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion from(String version) {
        return Arrays.stream(values())
                .filter(httpVersion -> httpVersion.version.equals(version))
                .findFirst().orElseThrow();
    }

    public String getVersion() {
        return version;
    }
}
