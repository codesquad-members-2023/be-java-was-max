package webserver.response;

import java.util.Arrays;
import java.util.Map;

public class Response {

    private StatusLine statusLine;
    private Map<String, String> header;
    private byte[] messageBody;

    public Response(StatusLine statusLine, Map<String, String> header, byte[] messageBody) {
        this.statusLine = statusLine;
        this.header = header;
        this.messageBody = messageBody;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", statusLine, header, Arrays.toString(messageBody));
    }
}
