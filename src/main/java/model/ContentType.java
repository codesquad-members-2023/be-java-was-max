package model;

public enum ContentType {

    HTML("Content-Type: text/html; charset=utf-8\r\n"),
    CSS("Content-Type: text/css; charset=utf-8\r\n"),
    JS("Content-Type: application/javascript\r\n"),
    ICO("Content-Type: image/x-icon\r\n"),
    PNG("Content-Type: image/png\r\n"),
    JPG("Content-Type: image/jpg\r\n");

    private final String typeMessage;

    private ContentType(String type) {
        this.typeMessage = type;
    }

    public String getTypeMessage() {
        return typeMessage;
    }
}
