package http.request;

import http.request.component.RequestHeader;
import http.request.component.RequestLine;
import java.io.BufferedReader;
import java.io.IOException;

public final class HttpRequestFactory {


    private HttpRequestFactory() {

    }

    public static HttpServletRequest create(BufferedReader br) throws IOException {
        RequestLine requestLine = HttpRequestParser.parseRequestLine(br.readLine());
        RequestHeader requestHeader = HttpRequestParser.parseHeader(br);
        return new HttpServletRequest(requestLine, requestHeader);
    }
}
