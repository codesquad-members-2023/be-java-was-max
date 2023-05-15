package util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public static Map<String, String> parseStartLine (String startLine) {
        Map<String, String> container = new HashMap<>();
        String[] splitLine = startLine.split(" ");

        container.put("Method", splitLine[0]);
        container.put("URL", splitLine[1]);
        container.put("Protocol", splitLine[2]);
        return container;
    }

    public static Map<String, String> parseHeader (String header) {
        Map<String, String> container = new HashMap<>();
        String[] splitLine = header.split(":");

        for (String line : splitLine) {
            line = line.trim();
        }
        container.put(splitLine[0], splitLine[1]);
        return container;
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
