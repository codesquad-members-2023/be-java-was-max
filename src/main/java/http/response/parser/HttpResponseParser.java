package http.response.parser;

import static http.common.header.EntityHeaderType.CONTENT_LENGTH;

import http.common.HttpStatus;
import http.common.header.HeaderType;
import http.common.header.HeaderTypeFactory;
import http.common.version.ProtocolVersion;
import http.response.HttpResponse;
import http.response.component.ResponseHeader;
import http.response.component.StatusLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseParser {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseParser.class);

    private static final String SEPARATOR = ":\\s*";

    public static HttpResponse parse(BufferedReader br) throws IOException {
        StatusLine statusLine = parseStatusLine(br.readLine());
        ResponseHeader responseHeader = parseHeader(br);
        int contentLength = 0;

        if (responseHeader.containsKey(CONTENT_LENGTH)) {
            contentLength = Integer.parseInt(responseHeader.getHeaderValue(CONTENT_LENGTH));
        }

        String messageBody = parseMessageBody(br, contentLength);

        return new HttpResponse(statusLine, responseHeader, messageBody.getBytes());
    }

    private static StatusLine parseStatusLine(String statusLine) {
        String[] statusLineTokens = statusLine.split(" ");
        String[] protocolTokens = statusLineTokens[0].split("[/.]");
        String protocol = protocolTokens[0];
        int major = Integer.parseInt(protocolTokens[1]);
        int minor = Integer.parseInt(protocolTokens[2]);
        ProtocolVersion protocolVersion = new ProtocolVersion(protocol, major, minor);
        int statusCode = Integer.parseInt(statusLineTokens[1]);
        HttpStatus httpStatus = HttpStatus.resolve(statusCode);
        return new StatusLine(protocolVersion, httpStatus);
    }

    private static ResponseHeader parseHeader(BufferedReader br) throws IOException {
        Map<HeaderType, String> header = new HashMap<>();

        String line;
        while (!(line = br.readLine()).isEmpty()) {
            String[] tokens = line.split(SEPARATOR);
            logger.debug("headerName : {}", tokens[0].trim());
            HeaderType key = HeaderTypeFactory.createHeaderType(tokens[0].trim());
            String value = tokens[1].trim();
            header.put(key, value);
        }

        return new ResponseHeader(header);
    }

    private static String parseMessageBody(BufferedReader br, int contentType) throws IOException {
        char[] buf = new char[contentType];
        br.read(buf, 0, contentType);
        return String.valueOf(buf);
    }
}
