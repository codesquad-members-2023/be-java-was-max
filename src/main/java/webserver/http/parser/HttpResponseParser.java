package webserver.http.parser;

import webserver.http.response.HttpResponse;
import webserver.http.response.component.ResponseHeader;
import webserver.http.response.component.StatusLine;

import java.io.BufferedReader;
import java.io.IOException;

import static webserver.http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static webserver.http.parser.HttpParser.readHttpHeader;
import static webserver.http.parser.HttpParser.readMessageBody;
import static webserver.http.response.component.StatusLine.parseStatusLine;

public class HttpResponseParser {

    private static final int DEFAULT_CONTENT_LENGTH = 0;

    public static HttpResponse parse(BufferedReader br) throws IOException {
        StatusLine statusLine = parseStatusLine(br.readLine());
        String headerString = readHttpHeader(br);

        ResponseHeader responseHeader = parseResponseHeader(headerString);
        int contentLength = Integer.parseInt(responseHeader.get(CONTENT_LENGTH)
                .orElse(String.valueOf(DEFAULT_CONTENT_LENGTH)));
        String messageBody = readMessageBody(br, contentLength);

        return new HttpResponse(statusLine, responseHeader, messageBody.getBytes());
    }


    private static ResponseHeader parseResponseHeader(String headerString) {
        return new ResponseHeader(HttpParser.parseHeaderMap(headerString));
    }
}
