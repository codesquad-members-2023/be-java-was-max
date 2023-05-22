package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.domain.HttpBody;
import servlet.domain.HttpRequest;
import servlet.domain.RequestHeaders;
import servlet.domain.StartLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestMessageParser {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestMessageParser.class);
    public static final String HEADER_DELIMITER = ":";
    public static final int HEADER_KEY_INDEX = 0;
    public static final int HEADER_VALUE_INDEX = 1;

    public static HttpRequest parsingHttpRequest(InputStream in)
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        char[] charBuffer = new char[3000];
        int bytesRead;
        if ((bytesRead = reader.read(charBuffer)) < 0) {
            throw new NoSuchMethodException();
        }
        stringBuilder.append(charBuffer, 0, bytesRead);
        logger.debug("{}", stringBuilder);
        String requestStr = stringBuilder.toString();
        String[] split = requestStr.split("\r\n");
        String startLineString = split[0];
        logger.debug("startLine = {}", startLineString);
        int index = 0;
        StartLine startLine = StartLine.parse(startLineString);
        Map<String, String> headers = new HashMap<>();
        index = parseHeaders(split, index, headers);
        RequestHeaders requestHeaders = RequestHeaders.from(headers);
        int contentLength = requestHeaders.getContentLength();
        if (contentLength != 0) {
            HttpBody httpBody = HttpBody.from(parseBody(index, split));
            return HttpRequest.of(startLine, requestHeaders, httpBody);
        }
        return HttpRequest.of(startLine, requestHeaders);
    }

    private static int parseHeaders(String[] split, int index, Map<String, String> headers) {
        index++;
        logger.debug("header = {}", split[index]);
        while (split.length > index && !Objects.equals(split[index], "")) {
            logger.debug("header = {}", split[index]);
            String[] headerKeyEntry = split[index].split(HEADER_DELIMITER);
            headers.put(headerKeyEntry[HEADER_KEY_INDEX], headerKeyEntry[HEADER_VALUE_INDEX].trim());
            index++;
        }
        return ++index;
    }

    private static Map<String, String> parseBody(int index, String[] requestInfo) {
        Map<String, String> body = new HashMap<>();
        String line = requestInfo[index];
        logger.debug("body = {}", line);
        for (String each : line.split("&")) {
            String[] keyValue = each.split("=");
            body.put(keyValue[0], keyValue[1]);
        }
        return body;
    }

}
