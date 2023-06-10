package util.response;

public enum ContentType {
    HTML("text/html", "html"),
    CSS("text/css", "css"),
    JAVASCRIPT("application/javascript", "js"),
    ICO("image/x-icon", "ico"),
    PNG("image/png", "png"),
    JPG("image/jpeg", "jpg"),
    WOFF("application/octet-stream", "woff"),
    TTF("application/octet-stream", "ttf");

    private final String contentType;
    private final String value;

    ContentType(String contentType, String value) {
        this.contentType = contentType;
        this.value = value;
    }

    public String getContentType() {
        return contentType;
    }

    public String getValue() {
        return value;
    }

    public static String equalsValue(String inputValue) {
        ContentType arr[]  = ContentType.values();
        for(ContentType temp : arr) {
            if(inputValue.equals(temp.getValue())) {
                return temp.getContentType();
            }
        }
        return HTML.contentType;
    }
}
