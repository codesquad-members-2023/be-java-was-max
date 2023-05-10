package webserver.response;

import java.util.Arrays;
import java.util.Map;

public class Response {

    private final StatusLine statusLine;
    private final ResponseHeader responseHeader;
    private final byte[] messageBody;

    public Response(StatusLine statusLine, ResponseHeader responseHeader, byte[] messageBody) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.messageBody = messageBody;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public Object getHeaderValue(String key) {
        return responseHeader.getHeaderValue(key);
    }

    public void addHeader(String key, Object value) {
        responseHeader.addHeader(key, value);
    }

    @Override
    public String toString() {
        return String.format("%s\r\n%s\r\n\r\n", statusLine, responseHeader);
    }
}
