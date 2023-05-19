package http.parser;

import static http.parser.HttpRequestParser.HEADER_SEPARATOR_REGEX;
import static http.parser.HttpRequestParser.KEY_INDEX;
import static http.parser.HttpRequestParser.VALUE_INDEX;
import static java.lang.System.lineSeparator;

import http.common.header.HeaderType;
import http.common.header.HeaderTypeFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {

    public static String readHttpHeader(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            sb.append(line).append(lineSeparator());
        }
        return sb.toString().trim();
    }

    public static Map<HeaderType, String> parseHeaderMap(String headerString) {
        Map<HeaderType, String> headerMap = new HashMap<>();
        String[] headers = headerString.split(System.lineSeparator());
        for (String header : headers) {
            HeaderType key = HeaderTypeFactory.createHeaderType(header.split(HEADER_SEPARATOR_REGEX)[KEY_INDEX]);
            if (key == null) {
                continue;
            }
            String value = header.split(HEADER_SEPARATOR_REGEX)[VALUE_INDEX];
            headerMap.put(key, value);
        }
        return headerMap;
    }

    public static String readMessageBody(BufferedReader br, int contentType) throws IOException {
        char[] buf = new char[contentType];
        br.read(buf, 0, contentType);
        return String.valueOf(buf);
    }
}
