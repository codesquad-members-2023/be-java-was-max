package webserver;

public class HttpRequest {
    public HttpRequest() {
    }

    public static String separateUrl(String line) {
        String url = line.split(" ")[1].split("\\?")[0];

        if (url.equals("/")) {
            url = "/index.html";
        }

        return url;
    }
}
