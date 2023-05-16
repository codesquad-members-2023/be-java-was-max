package http.response.component;

public enum ContentType {
    AAC("audio/aac"),
    ABW("applicatoin/x-abiword"),
    ARC("application/octet-stream"),
    AVI("video/x-msvideo"),
    AZW("application/vnd.amazon.ebook"),
    BIN("application/octet-stream"),
    BZ("application/x-bzip"),
    BZ2("application/x-bzip2"),
    CSH("application/x-csh"),
    CSS("text/css"),
    CSV("text/csv"),
    DOC("application/msword"),
    EPUB("application/epub+zip"),
    GIF("image/gif"),
    HTM("text/html"),
    HTML("text/html"),
    ICO("image/x-icon"),
    ICS("text/calendar"),
    JAR("application/java-archive"),
    JPEG("image/jpeg"),
    JPG("image/jpeg"),
    JS("text/javascript"),
    JSON("application/json"),
    MID("audio/midi"),
    MIDI("audio/midi"),
    MPEG("video/mpeg"),
    MPKG("application/vnd.apple.installer+xml"),
    ODP("application/vnd.oasis.opendocument.presentation"),
    ODS("application/vnd.oasis.opendocument.spreadsheet"),
    ODT("application/vnd.oasis.opendocument.text"),
    OGA("audio/ogg"),
    OGV("video/ogg"),
    OGX("application/ogg"),
    PDF("application/pdf"),
    PNG("image/png"),
    PPT("application/vnd.ms-powerpoint"),
    RAR("application/x-rar-compressed"),
    RTF("application/rtf"),
    SH("application/x-sh"),
    SVG("image/svg+xml"),
    SWF("application/x-shockwave-flash"),
    TAR("application/x-tar"),
    TIF("image/tiff"),
    TIFF("image/tiff"),
    TTF("application/x-font-ttf"),
    VSD("application/vnd.visio"),
    WAV("audio/x-wav"),
    WEBA("audio/webm"),
    WEBM("video/webm"),
    WEBP("image/webp"),
    WOFF("application/x-font-woff"),
    XHTML("application/xhtml+xml"),
    XLS("application/vnd.ms-excel"),
    XML("application/xml"),
    XUL("application/vnd.mozilla.xul+xml"),
    ZIP("application/zip");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public static ContentType resolve(String uri) {
        for (ContentType c : values()) {
            if (uri.toUpperCase()
                .endsWith(c.name())) {
                return c;
            }
        }
        throw new RuntimeException("찾을 수 없는 컨텐츠 타입입니다. : " + uri);
    }

    @Override
    public String toString() {
        return value;
    }
}
