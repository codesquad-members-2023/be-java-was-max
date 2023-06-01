package webserver.http.response;

import webserver.http.common.header.HeaderType;
import webserver.http.response.component.ResponseHeader;
import webserver.http.response.component.StatusLine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpResponse {

    private StatusLine statusLine;
    private ResponseHeader responseHeader;
    private byte[] messageBody;
    private ByteArrayOutputStream messageBodyOutputStream;
    private OutputStreamWriter messageBodyWriter;

    public HttpResponse() {
        this.statusLine = null;
        this.responseHeader = new ResponseHeader(new HashMap<>());
        this.messageBody = new byte[0];
        this.messageBodyOutputStream = new ByteArrayOutputStream();
        this.messageBodyWriter = new OutputStreamWriter(messageBodyOutputStream, UTF_8);
    }

    public HttpResponse(StatusLine statusLine, ResponseHeader responseHeader, byte[] messageBody) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.messageBody = messageBody;
    }

    public void addHeader(HeaderType key, String value) {
        responseHeader.put(key, value);
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

    public Optional<String> get(HeaderType key) {
        return responseHeader.get(key);
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public ByteArrayOutputStream getMessageBodyOutputStream() {
        return messageBodyOutputStream;
    }

    public OutputStreamWriter getMessageBodyWriter() {
        return messageBodyWriter;
    }

    @Override
    public String toString() {
        return String.format("%s\r\n%s\r\n\r\n", statusLine, responseHeader);
    }
}
