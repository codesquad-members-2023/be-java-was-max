package http;

import com.google.common.collect.Maps;
import http.response.ContentType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpUtils {

    private static final String URL_DIVIDER = "&";
    private static final String QUERY_STRING_DIVIDER = "=";
    private static final String HEADER_DIVIDER = ":";
    private static final String EXTENSION_DIVIDER = ".";
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;
    private static final String HTML_EXTENSION = ".html";
    private static final String STATIC_PATH = "src/main/resources/static";
    private static final String TEMPLATES_PATH = "src/main/resources/templates";

    private HttpUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> parameters = Maps.newHashMap();
        String[] tokens = queryString.split(URL_DIVIDER);

        for (String token : tokens) {
            StringTokenizer tokenizer = new StringTokenizer(token, QUERY_STRING_DIVIDER);
            parameters.put(tokenizer.nextToken(), tokenizer.nextToken());
        }

        return parameters;
    }

    public static Map<String, String> parseHeader(List<String> header) {
        Map<String, String> headers = Maps.newHashMap();

        for (String line : header) {
            String[] split = line.split(HEADER_DIVIDER);
            String key = split[HEADER_KEY_INDEX];
            String value = split[HEADER_VALUE_INDEX].strip();
            headers.put(key, value);
        }

        return headers;
    }

    public static HttpMethod getMethodType(String name) {
        if (name.equals(HttpMethod.POST.name())) {
            return HttpMethod.POST;
        }

        return HttpMethod.GET;
    }

    public static byte[] findFilePath(String url) throws IOException {
        // Templates
        if (url.endsWith(HTML_EXTENSION)) {
            return Files.readAllBytes(new File(TEMPLATES_PATH + url).toPath());
        }
        // Static
        return Files.readAllBytes(new File(STATIC_PATH + url).toPath());
    }

    public static String findContentType(String url) {
        String extension = url.substring(url.lastIndexOf(EXTENSION_DIVIDER));
        if (extension.equals(ContentType.JS.getExtension())) {
            return ContentType.JS.getType();
        }
        if (extension.equals(ContentType.CSS.getExtension())) {
            return ContentType.CSS.getType();
        }
        if (extension.equals(ContentType.PNG.getExtension())) {
            return ContentType.PNG.getType();
        }
        if (extension.equals(ContentType.JPEG.getExtension())
                || extension.equals(ContentType.JPG.getExtension())) {
            return ContentType.JPEG.getType();
        }
        if (extension.equals(ContentType.TRUE_TYPE_FONT.getExtension())
                || extension.equals(ContentType.WEB_OPEN_FONT.getExtension())) {
            return ContentType.TRUE_TYPE_FONT.getType();
        }
        return ContentType.HTML.getType();
    }
}
