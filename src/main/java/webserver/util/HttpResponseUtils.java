package webserver.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static webserver.util.HttpStatus.OK;

public class HttpResponseUtils {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final Map<Integer, String> STATUS_MESSAGE = Map.of(200, "OK", 302, "FOUND");

    private int statusCode;
    private Map<String, String> headers = new HashMap<>();
    private byte[] messageBody = {};
    private String redirectUrl;

    public void setStatusCode(int code) {
        statusCode = code;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isRedirect() {
        return statusCode / 100 == 3;
    }

    public void setCookie(String cookieName, String cookieValue) {
        addHeader("Set-Cookie", cookieName + "=" + cookieValue + "; Path=/");
    }

    public void setContent(String absolutePath, HttpRequestUtils httpRequest) throws IOException {
        if (isRedirect()) {
            addHeader("Location", redirectUrl);
            return;
        }

        this.messageBody = Files.readAllBytes(new File(absolutePath).toPath());
        addHeader("Content-Type", httpRequest.getContentType().getMimeType());
        addHeader("Content-Length", String.valueOf(messageBody.length));
    }

    public byte[] toBytes() {
        StringBuilder sb = new StringBuilder();

        if (!isRedirect()) {
            statusCode = OK;
        }

        sb.append(HTTP_VERSION + " ").append(statusCode).append(" ").append(STATUS_MESSAGE.get(statusCode)).append(" \r\n");

        headers.forEach((k, v) -> {
            sb.append(k).append(": ").append(v).append("\r\n");
        });
        sb.append("\r\n");

        return sb.toString().getBytes();
    }

    public byte[] getMessageBody() {
        return messageBody;
    }
}
