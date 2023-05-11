package model;

import java.util.Map;

public class Line {
    private String url;
    private final String method;
    private final Map<String, String> queryMap;

    public Line(String method, String url, Map<String, String> queryMap) {
        this.method = method;
        this.url = url;
        this.queryMap = queryMap;
    }

    public String separateAbsolutePath() {
        if (url.startsWith("/css") || url.startsWith("/js") || url.startsWith("/fonts") || url.endsWith(".ico") || url.endsWith(".png")) {
            return "src/main/resources/static" + url;
        }

        if (url.equals("/")) {
            url = "/index.html";
        }

        return "src/main/resources/templates" + url;
    }

    public String separateRequestType() {
        int start = url.indexOf("/") + 1;  // 첫 번째 "/" 위치
        int end = url.indexOf("/", start);  // 두 번째 "/" 위치

        // 두번쨰 "/"를 찾았다면
        if (end != -1) {
            String requestType = url.substring(start, end);
            if (requestType.equals("css") || requestType.equals("js") || requestType.equals("fonts")) {
                return requestType;
            }

            return "html";
        }

        return "html";
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getQueryMap() {
        return queryMap;
    }
}
