package http;

import model.ContentType;
import model.Response;
import model.ResponseHeaders;
import model.StatusLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResponseMaker {

    public Response make(int statusCode, String url) throws IOException {
        StatusLine statusLine = makeStatusLine(statusCode);
        byte[] body = null;
        if ((statusCode / 100) == 2) {
            body = makeBody(url);
        }
        ResponseHeaders headers;
        switch (statusCode / 100) {
            case 2:
                headers = makeSuccessHeaders(body.length, url);
                break;
            case 3:
                headers = makeRedirectionHeaders(url);
                break;
            default:
                headers = null;
                break;
        }
        return new Response(statusLine, headers, body);
    }

    private StatusLine makeStatusLine(int statusCode) {
        return new StatusLine(statusCode);
    }

    private ResponseHeaders makeSuccessHeaders(int bodyLength, String url) {
        ResponseHeaders headers = new ResponseHeaders();

        headers.add("Content-Type", makeContentTypeHeader(url));
        headers.add("Content-Length", String.valueOf(bodyLength));
        return headers;
    }

    private ResponseHeaders makeRedirectionHeaders(String url) {
        ResponseHeaders headers = new ResponseHeaders();

        headers.add("Location", url);
        return headers;
    }

    private byte[] makeBody(String url) throws IOException {
        if (url.endsWith(".html")) {
            return Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath());
        }
        return Files.readAllBytes(new File("./src/main/resources/static" + url).toPath());
    }

    private String makeContentTypeHeader(String url) {
        String[] splitContentType = url.split("\\.");
        String fileName = splitContentType[splitContentType.length - 1];
        String upperFileName = fileName.toUpperCase();

        return ContentType.valueOf(upperFileName).getTypeMessage();
    }
}
