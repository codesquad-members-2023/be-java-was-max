package http.response;

public enum ContentType {
    HTML("text/html;charset=utf-8", ".html"),
    CSS("text/css", ".css"),
    JS("application/javascript", ".js"),
    PNG("image/png", ".png"),
    JPEG("image/jpeg", ".jpeg"),
    JPG("image/jpg", ".jpg"),
    TRUE_TYPE_FONT("application/octet-stream", ".ttf"),
    WEB_OPEN_FONT("application/octet-stream", ".woff");

    private final String type;
    private final String extension;

    ContentType(String type, String extension){
        this.type = type;
        this.extension = extension;
    }

    public String getType() {
        return type;
    }

    public String getExtension() {
        return extension;
    }

    public static String getTypeValue(String extension) {
        if (extension.equals(JS.getExtension())) {
            return JS.getType();
        }
        if (extension.equals(CSS.getExtension())) {
            return CSS.getType();
        }
        if (extension.equals(PNG.getExtension())) {
            return PNG.getType();
        }
        if (extension.equals(JPEG.getExtension())
                || extension.equals(JPG.getExtension())) {
            return JPEG.getType();
        }
        if (extension.equals(TRUE_TYPE_FONT.getExtension())
                || extension.equals(WEB_OPEN_FONT.getExtension())) {
            return TRUE_TYPE_FONT.getType();
        }
        return HTML.getType();
    }
}
