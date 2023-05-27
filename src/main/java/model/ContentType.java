package model;

public enum ContentType {

    HTML("text/html; charset=utf-8"),
    CSS("text/css; charset=utf-8"),
    JS("application/javascript"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpg"),
    WOFF("application/woff"),
    TTF("application/x-font-ttf");

    private final String typeMessage;

    private ContentType(String type) {
        this.typeMessage = type;
    }

    public String getTypeMessage() {
        return typeMessage;
    }
}
