package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.domain.Headers;
import servlet.domain.HttpRequest;
import servlet.domain.StartLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestMessageParser {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestMessageParser.class);
    public static final String HEADER_DELIMITER = ":";
    public static final int HEADER_KEY_INDEX = 0;
    public static final int HEADER_VALUE_INDEX = 1;

    public static HttpRequest parsingHttpRequest(InputStream in)
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String startLineString = reader.readLine();
        logger.debug("startLine = {}", startLineString);
        StartLine startLine = StartLine.parse(startLineString);
        Headers headers = Headers.from(parseHeaders(reader));

        return HttpRequest.of(startLine, headers);
    }

    private static Map<String, String> parseHeaders(BufferedReader reader) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line = reader.readLine();
        while (line != null && !line.isBlank()) {
            String[] headerKeyEntry = line.split(HEADER_DELIMITER);
            headers.put(headerKeyEntry[HEADER_KEY_INDEX], headerKeyEntry[HEADER_VALUE_INDEX].trim());
            line = reader.readLine();
        }
        return headers;
    }

}
