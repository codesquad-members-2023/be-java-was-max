package util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public static void parseStartLine (String startLine, Map<String, String> container) {
        String[] splitLine = startLine.split(" ");

        container.put("Method", splitLine[0]);
        container.put("URL", splitLine[1]);
        container.put("Protocol", splitLine[2]);
    }

    public static void parseHeader (String header, Map<String, String> container) {
        String[] splitLine = header.split(": ");

        container.put(splitLine[0], splitLine[1]);
    }

    public static Map<String, String> parseURL(String URL) throws UnsupportedEncodingException {
        Map<String, String> dto = new HashMap<>();
        String[] URLSplit = URL.split("\\?", 2);
        String[] form = URLSplit[1].split("&");

        for (String element : form) {
            String[] field = element.split("=");
            dto.put(field[0], URLDecoder.decode(field[1], "UTF-8"));
        }
        return dto;
    }
}
