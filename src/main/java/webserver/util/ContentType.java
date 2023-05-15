package webserver.util;

import java.util.Arrays;

public enum ContentType {

    HTML("src/main/resources/templates", "text/html;charset=utf-8", ".html"),
    CSS("src/main/resources/static", "text/css", ".css"),
    JS("src/main/resources/static", "application/javascript", ".js"),
    FONTS("src/main/resources/static", "application/octet-stream", ".woff"),
    ICO("src/main/resources/static", "image/avi", ".ico"),
    PNG("src/main/resources/static", "image/png", ".png");

    private final String path;
    private final String contentType;
    private final String fileExtension;

    ContentType(String path, String contentType, String fileExtension) {
        this.path = path;
        this.contentType = contentType;
        this.fileExtension = fileExtension;
    }

    public static ContentType findByUrl(String url) {
        return Arrays.stream(values())
                .filter(contentType -> url.endsWith(contentType.fileExtension))
                .findAny()
                // TODO: 예외 던졌으니 처리 필요함 (호눅스 피드백)
                .orElseThrow(() -> new RuntimeException("Unknown file extension: " + url));
    }

    public String separatePath(String url) {
        if (url.equals("/")) {
            url = path + "/index.html";
        }

        return path + url;
    }

    public String getContentType() {
        return contentType;
    }
}
