package util.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    private static final Logger logger = LoggerFactory.getLogger(HttpHeader.class);

    private BufferedReader br;
    private final String requestLine;
    private Map<String, String> allRequest;
    private String body;
    private Map<String, String> bodyParam;


    public HttpHeader(BufferedReader br) throws IOException {
        this.br = br;
        this.requestLine = convertToRequestLine(br);
        this.allRequest = createAllRequest();
        this.body = readBody();
        this.bodyParam = HttpRequestUtil.paresQueryString(body);
    }

    private String convertToRequestLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    private String readBody() throws IOException {
        if(allRequest.containsKey("Content-Length")) {
           int contentLength = Integer.parseInt(allRequest.get("Content-Length").trim());
            char[] buffer = new char[contentLength];
            int bytesRead = br.read(buffer);
            logger.debug("body: {}" ,String.valueOf(buffer,0,bytesRead) );
            return String.valueOf(buffer,0,bytesRead);
        }
        return "";
    }

    private Map<String, String> createAllRequest() throws IOException {
        Map<String, String> map = new HashMap<>();
        String line = br.readLine();
        logger.debug("request line : {}" , requestLine);
        while (!line.equals("")) {
            map.put(line.split(":")[0], line.split(":")[1]);
            logger.debug("header : {}" , line);
            line = br.readLine();
        }
        return map;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getBodyParam() {
        return bodyParam;
    }
}
