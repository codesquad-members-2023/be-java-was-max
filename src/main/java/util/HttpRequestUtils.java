package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.domain.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class HttpRequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
    public static final String HTTP_SIGN = "%40";
    public static final String UTF8_SIGN = "@";
    public static final String PARAMETER_DELIMITER = "&";
    public static final String HTTP_METHOD_AND_PARAMETER_DELIMITER = "=";
    public static final String HTTP_METHOD_DELIMITER = "\\?";
    public static final String LINE_DELIMITER = " ";
    public static final String CONTAINS_PARAMETER_DELIMITER = "?";
    public static final String HEAD_CASE_DELIMITER = "-";
    public static final String HEAD_NAME_SUFFIX = ":";
    public static final String SUFFIX_REPLACEMENT = "";

    public static HttpRequest parsingHttpRequest(InputStream in)
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = reader.readLine();
        HttpRequest.Builder builder = new HttpRequest.Builder();
        if (line != null && !line.isBlank()) {
            buildHTTPMethod(line, builder);
        }
        line = reader.readLine();
        while (!line.isBlank()) {
            builderHeader(line, builder);
            line = reader.readLine();
        }
        return builder.build();
    }

    private static void builderHeader(String line, HttpRequest.Builder builder)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String[] keyValue = line.split(LINE_DELIMITER);
        logger.debug("{} = {}", keyValue[0], keyValue[1]);
        String name = HttpRequestUtils.parsingHeaderName(keyValue[0]);

        Method method = builder.getClass().getMethod(name, String.class);
        method.invoke(builder, keyValue[1]);
    }


    private static void buildHTTPMethod(String line, HttpRequest.Builder builder) {
        logger.debug("requestUrl = {}", line);
        String[] httpMethodInfo = line.split(LINE_DELIMITER);
        builder.method(httpMethodInfo[0]);
        if (httpMethodInfo[1].contains(CONTAINS_PARAMETER_DELIMITER)) {
            String[] httpMethod = HttpRequestUtils.parseHttpMethod(httpMethodInfo[1]);
            builder.url(httpMethod[0]);
            HashMap<String, String> map = HttpRequestUtils.parseQueryString(httpMethod);
            builder.parameters(map);
        } else {
            builder.url(httpMethodInfo[1]);
        }
        builder.httpVersion(httpMethodInfo[2]);
    }

    public static String parsingHeaderName(String head) {
        head = head.replace(HEAD_NAME_SUFFIX, SUFFIX_REPLACEMENT).toLowerCase();
        if (head.contains(HEAD_CASE_DELIMITER)) {
            String[] split = head.split(HEAD_CASE_DELIMITER);
            StringBuilder sb = new StringBuilder(split[0]);
            appendHeader(split, sb);
            head = sb.toString();
        }
        return head;
    }

    private static void appendHeader(String[] split, StringBuilder sb) {
        for (int i = 1; i < split.length; i++) {
            sb.append(String.valueOf(split[i].charAt(0)).toUpperCase());
            sb.append(split[i].substring(1));
        }
    }

    public static HashMap<String, String> parseQueryString(String[] httpMethod) {
        HashMap<String, String> map = new HashMap<>();
        String[] parameters = httpMethod[1].split(PARAMETER_DELIMITER);
        for (String parameter : parameters) {
            String[] keyValue = parameter.split(HTTP_METHOD_AND_PARAMETER_DELIMITER);
            map.put(keyValue[0], parsingValue(keyValue[1]));
        }
        return map;
    }

    private static String parsingValue(String value) {
        if (value.contains(HTTP_SIGN)) {
            value = value.replace(HTTP_SIGN, UTF8_SIGN);
        }
        return value;
    }

    public static String[] parseHttpMethod(String httpMethod) {
        return httpMethod.split(HTTP_METHOD_DELIMITER);
    }
}
