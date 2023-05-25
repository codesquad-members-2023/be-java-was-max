package request;

public class RequestLineParser {
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int REQUEST_URI_INDEX = 1;
    private final String httpMethod;
    private String requestURI;

    public RequestLineParser(String inputLine) {
        String[] parsedLine = parseLine(inputLine);
        this.httpMethod = parsedLine[HTTP_METHOD_INDEX];
        this.requestURI = parsedLine[REQUEST_URI_INDEX];
    }

    private String[] parseLine(String inputLine) {
        return inputLine.split(" ");
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getRequestURI() {
        if (requestURI.equals("/user/create")) {
            return requestURI = "/index.html";
        }
        return requestURI;
    }
}
