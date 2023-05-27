package util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private RequestParser() {
    }

    public static String[] parseRequestLine(String rawRequestLine) {
        return rawRequestLine.split(" ");
    }

    public static String[] parseHeader(String rawHeader) {
        String[] splitHeader = rawHeader.split(":");
        String[] parsedHeader = new String[splitHeader.length];

        for (int index = 0; index < splitHeader.length; index++) {
            parsedHeader[index] = splitHeader[index].trim();
        }
        return parsedHeader;
    }

    public static String decodeBody(String rawBody) throws UnsupportedEncodingException {
        return URLDecoder.decode(rawBody, "UTF-8");
    }

    public static String[] parseUrl (String url) {
        return url.split("/");
    }

    public static Map<String, String> parseBody(String body) {
        Map<String, String> form = new HashMap<>();
        String[] splitBody = body.split("&");

        for (String element : splitBody) {
            String[] field = element.split("=");
            form.put(field[0], field[1]);
        }
        return form;
    }
}
