package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Map<String, String> request;
    private StringBuilder requestHeader;

    public HttpRequest(){}

    public HttpRequest(InputStream in) throws IOException {
        this.request = new HashMap<>();
        this.requestHeader = new StringBuilder();
        makeRequest(in);
    }

    private void makeRequest(InputStream in) throws IOException { // request와 requestHeader 만들기
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();  // method, url, (http)version
        String[] splitedLine = line.split(" ");
        request.put("method", splitedLine[0]);
        request.put("url", splitedLine[1]);
        request.put("version", splitedLine[2]);
        requestHeader.append(line).append("\n");

        while (!(line = br.readLine()).equals("")) {
            splitedLine = line.split(": ");
            request.put(splitedLine[0], splitedLine[1]); // Map에 request header 넣기
        }

        requestHeader.append("Host: ").append(request.get("Host")).append("\n");
        requestHeader.append("Connection: ").append(request.get("Connection")).append("\n");
        requestHeader.append("Accept: */*");
    }

    public String getRequestHeader(){
        return requestHeader.toString();
    }

    public void printRequests(Map<String, String> request){
        for (String key: request.keySet()){
            logger.debug("{}: {}", key, request.get(key));
        }
    }

    public String getUrl(){
        return request.get("url");
    }
}
