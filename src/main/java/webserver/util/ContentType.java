package webserver.util;

import java.util.Arrays;

import static webserver.util.ContentType.Url.BASE_URL;


public enum ContentType {
    HTML(BASE_URL + "/templates", "text/html;charset=utf-8", ".html"),
    CSS(BASE_URL + "/static", "text/css", ".css"),
    JS(BASE_URL + "/static", "application/javascript", ".js"),
    FONTS(BASE_URL + "/static", "application/octet-stream", ".woff"),
    ICO(BASE_URL + "/static", "image/avi", ".ico"),
    PNG(BASE_URL + "/static", "image/png", ".png");

    static class Url {
        public static final String BASE_URL = "src/main/resources";
    }

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
