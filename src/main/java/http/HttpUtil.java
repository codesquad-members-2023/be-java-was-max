package http;

import com.google.common.collect.Maps;
import http.response.ContentType;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static http.HttpMethod.GET;
import static http.HttpMethod.POST;


public final class HttpUtil {

    private static final String PARAMETER_DIVIDER = "&";
    private static final String KEY_VALUE_DIVIDER = "=";
    private static final String HEADER_DIVIDER = ":";
    private static final String EXTENSION_DIVIDER = ".";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private HttpUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> parameters = Maps.newHashMap();
        String[] tokens = queryString.split(PARAMETER_DIVIDER);

        for (String token : tokens) {
            StringTokenizer tokenizer = new StringTokenizer(token, KEY_VALUE_DIVIDER);
            parameters.put(tokenizer.nextToken(), tokenizer.nextToken());
        }

        return parameters;
    }

    public static Map<String, String> parseHeader(List<String> header) {
        Map<String, String> headers = Maps.newHashMap();

        for (String line : header) {
            String[] split = line.split(HEADER_DIVIDER);
            String key = split[KEY_INDEX];
            String value = split[VALUE_INDEX].strip();
            headers.put(key, value);
        }

        return headers;
    }

    public static Map<String, String> parseBody(String body) {
        Map<String, String> data = Maps.newHashMap();

        String[] parameters = body.split(PARAMETER_DIVIDER);
        for (String parameter : parameters) {
            String key = parameter.split(KEY_VALUE_DIVIDER)[KEY_INDEX];
            String value = parameter.split(KEY_VALUE_DIVIDER)[VALUE_INDEX];
            data.put(key, value);
        }

        return data;
    }

    public static HttpMethod getMethodType(String name) {
        if (name.equals(POST.name())) {
            return POST;
        }

        return GET;
    }

    public static String findContentTypeValue(String url) {
        String extension = url.substring(url.lastIndexOf(EXTENSION_DIVIDER));
        return ContentType.getTypeValue(extension);
    }
}
