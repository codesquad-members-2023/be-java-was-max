package webserver;

public enum MediaType {
    // TODO extension 수정 필요, contentType을 요청 메세지에서 가져올지 고민해보기
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    IMAGE_PNG("png", "image/png"),
    ICO("ico", "image/png"),
    FONT_EOT("eot", "application/vnd.ms-fontobject"),
    FONT_SVG("svg", "image/svg+xml"),
    FONT_TTF("ttf", "application/x-font-ttf"),
    FONT_WOFF("woff", "application/font-woff"),
    FONT_WOFF2("woff2", "font/woff2");

    private static final String TEMPLATES_PATH = "./src/main/resources/templates";
    private static final String STATIC_PATH = "./src/main/resources/static";

    public final String extension;
    public final String contentType;
    public final String path;

    MediaType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
        this.path = getPath();
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPath() {
        if (this == HTML) {
            return TEMPLATES_PATH;
        }
        return STATIC_PATH;
    }
}
