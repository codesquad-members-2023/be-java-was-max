package response;

import javax.swing.text.html.CSS;

public enum ContentType {
    HTML("text/html;charset=utf-8"),
    CSS("text/css"),
    JS("application/javascript"),
    PNG("image/png"),
    JPEG("image/jpeg"),
    FONT("application/octet-stream");

    private String type;

    ContentType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
