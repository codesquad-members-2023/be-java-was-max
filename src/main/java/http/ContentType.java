package http;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("text/javascript"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpeg"),
    EOT("application/vds.ms-fontobject"),
    SVG("image/svg+xml"),
    TTF("application/x-font-ttf"),
    WOFF("application/font-woff"),
    WOFF2("font/woff2");

    private final String value;
    ContentType(String value) {
        this.value = value;
    }

    public static String get(final String viewPath){
        String extension = viewPath.substring(viewPath.lastIndexOf(".")+1);
        for (ContentType ct: ContentType.values()){
            if (extension.toUpperCase().equals(ct.name())){
                return ct.value;
            }
        }
        throw new RuntimeException("지원하지 않는 확장자 입니다.");
    }

}

