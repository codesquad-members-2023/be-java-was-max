package response;

public enum ContentType {
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
    public final String mimeType;
    public final String path;

    ContentType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
        this.path = getPath();
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getPath() {
        if (this == HTML) {
            return TEMPLATES_PATH;
        }
        return STATIC_PATH;
    }
}
