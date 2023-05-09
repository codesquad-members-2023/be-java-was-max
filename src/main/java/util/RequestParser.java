package util;

import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public static Map<String, String> parseStartLine (String startLine) {
        Map<String, String> parsedStartLine = new HashMap<>();
        String[] split = startLine.split(" ");

        parsedStartLine.put("Method", split[0]);
        parsedStartLine.put("Url", split[1]);
        parsedStartLine.put("Protocol", split[2]);
        return parsedStartLine;
    }

    public static Map<String, String> parseHeader (String header) {
        Map<String, String> parsedHeader = new HashMap<>();
        String[] splitLine = header.split(System.lineSeparator());

        for (String line : splitLine) {
            String[] split = line.split(": ");
            parsedHeader.put(split[0], split[1]);
        }
        return parsedHeader;
    }
}
