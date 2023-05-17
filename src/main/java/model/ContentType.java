package model;

import java.util.Arrays;

import static model.ContentType.Url.BASE_URL;


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
    private final String mimeType;

    private final String fileExtension;

    ContentType(String path, String mimeType, String fileExtension) {
        this.path = path;
        this.mimeType = mimeType;
        this.fileExtension = fileExtension;
    }

    // TODO: RequestLine으로 변경 다시 해보자
    public static ContentType findByUrl(String url) {
        return Arrays.stream(values())
                .filter(mimeType -> url.endsWith(mimeType.fileExtension))
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

    public String getMimeType() {
        return mimeType;
    }
}
