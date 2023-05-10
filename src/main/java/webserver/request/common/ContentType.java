package webserver.request.common;

public enum ContentType {
    HTML("text/html;charset=utf-8"),
    CSS("text/css;charset=utf-8"),
    JS("application/javascript;charset=utf-8"),
    ICO("image/vnd.microsoft.icon"),
    PNG("image/png"),
    JPG("image/jpeg"),
    TTF("font/ttf"),
    WOFF("font/woff");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public static ContentType of(String uri) {
        for (ContentType c : values()) {
            if (uri.toUpperCase().endsWith(c.name())) {
                return c;
            }
        }
        throw new RuntimeException("일치하는 확장자가 없습니다.");
    }

    @Override
    public String toString() {
        return value;
    }
}
