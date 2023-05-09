package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class HttpRequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

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
        String[] keyValue = line.split(" ");
        logger.debug("{} = {}", keyValue[0], keyValue[1]);
        String name = HttpRequestUtils.parsingHeaderName(keyValue[0]);

        Method method = builder.getClass().getMethod(name, String.class);
        method.invoke(builder, keyValue[1]);
    }


    private static void buildHTTPMethod(String line, HttpRequest.Builder builder) {
        logger.debug("requestUrl = {}", line);
        String[] httpMethodInfo = line.split(" ");
        builder.method(httpMethodInfo[0]);
        if (httpMethodInfo[1].contains("?")) {
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
        head = head.replace(":", "").toLowerCase();
        if (head.contains("-")) {
            String[] split = head.split("-");
            StringBuilder sb = new StringBuilder(split[0]);
            for (int i = 1; i < split.length; i++) {
                sb.append(String.valueOf(split[i].charAt(0)).toUpperCase());
                sb.append(split[i].substring(1));
            }
            head = sb.toString();
        }
        return head;
    }

    public static HashMap<String, String> parseQueryString(String[] httpMethod) {
        HashMap<String, String> map = new HashMap<>();
        String[] parameters = httpMethod[1].split("&");
        for (String parameter : parameters) {
            String[] keyValue = parameter.split("=");
            map.put(keyValue[0], parsingValue(keyValue[1]));
        }
        return map;
    }

    private static String parsingValue(String value) {
        if (value.contains("%40")) {
            value = value.replace("%40", "@");
        }
        return value;
    }

    public static String[] parseHttpMethod(String httpMethod) {
        return httpMethod.split("\\?");
    }
}
