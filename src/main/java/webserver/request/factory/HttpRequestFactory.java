package webserver.request.factory;

import static webserver.request.parser.HttpRequestParser.parseHeader;
import static webserver.request.parser.HttpRequestParser.parseRequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import webserver.request.HttpRequest;
import webserver.request.component.RequestHeader;
import webserver.request.component.RequestLine;

public final class HttpRequestFactory {


    private HttpRequestFactory() {

    }

    public static HttpRequest create(BufferedReader br) throws IOException {
        RequestLine requestLine = parseRequestLine(br.readLine());
        RequestHeader requestHeader = parseHeader(br);
        return new HttpRequest(requestLine, requestHeader);
    }
}
