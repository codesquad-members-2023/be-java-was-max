package http.response;

import java.util.HashMap;

public class HttpServletResponse {

    private StatusLine statusLine;
    private ResponseHeader responseHeader;
    private byte[] messageBody;

    public HttpServletResponse() {
        this.statusLine = null;
        this.responseHeader = new ResponseHeader(new HashMap<>());
        this.messageBody = null;
    }

    public HttpServletResponse(StatusLine statusLine, ResponseHeader responseHeader,
        byte[] messageBody) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.messageBody = messageBody;
    }

    public void addHeader(String key, Object value) {
        responseHeader.addHeader(key, value);
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

    public Object getHeaderValue(String key) {
        return responseHeader.getHeaderValue(key);
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    @Override
    public String toString() {
        return String.format("%s\r\n%s\r\n\r\n", statusLine, responseHeader);
    }
}
