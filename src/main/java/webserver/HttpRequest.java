package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

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
        String line = br.readLine();  // method, url, httpVersion
        String[] splitedLine = line.split(" ");
        String method = splitedLine[0];
        String url = splitedLine[1];
        String httpVersion = splitedLine[2];

        request.put("method", method);
        request.put("url", url);
        request.put("version", httpVersion);

        requestHeader.append(method).append(" ").append(url).append("\n");
        requestHeader.append(httpVersion).append("\n");

        if (url.contains("/user/create")) { // 회원 가입 페이지에서 회원 정보를 받으면 실행
            User user = createUser(url);
        }

        while (!(line = br.readLine()).equals("")) { // Request Header
            splitedLine = line.split(": ");
            request.put(splitedLine[0], splitedLine[1]); // Map에 request header 넣기
        }

        requestHeader.append("Host: ").append(request.get("Host")).append("\n");
        requestHeader.append("Connection: ").append(request.get("Connection")).append("\n");
        requestHeader.append("Accept: */*");
    }

    private User createUser(String url){
        String[] splitedUrl = url.split("\\?");
        HttpRequestUtils utils = new HttpRequestUtils();
        Map<String, String> parsedStr = utils.parseQueryString(splitedUrl[1]);
        return utils.createUser(parsedStr);
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
