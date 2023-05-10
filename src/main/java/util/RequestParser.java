package util;

import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public static void parseStartLine (String startLine, Map<String, String> container) {
        String[] splitLine = startLine.split(" ");

        container.put("Method", splitLine[0]);
        container.put("Url", splitLine[1]);
        container.put("Protocol", splitLine[2]);
    }

    public static void parseHeader (String header, Map<String, String> container) {
        String[] splitLine = header.split(": ");

        container.put(splitLine[0], splitLine[1]);
    }
}
