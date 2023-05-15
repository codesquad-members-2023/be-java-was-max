package webserver;

import model.ContentType;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

public class Responsor {

    public byte[] makeResponse(String url) throws IOException {
        StringBuffer responseBuffer = new StringBuffer();
        byte[] body = makeBody(url);

        make200Header(responseBuffer, body.length, url);
        byte[] responseMessage = responseBuffer.toString().getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(body.length + responseMessage.length);
        byteBuffer.put(responseMessage);
        byteBuffer.put(body);
        return byteBuffer.array();
    }

    private byte[] makeBody(String url) throws IOException {
        if (url.endsWith(".html")) {
            return Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath());
        }
        return Files.readAllBytes(new File("./src/main/resources/static" + url).toPath());
    }

    private void make200Header(StringBuffer sb, int lengthOfBodyContent, String url) {
        sb.append("HTTP/1.1 200 OK \r\n");
        sb.append(makeContentTypeHeader(url));
        sb.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        sb.append("\r\n");
    }

    private String makeContentTypeHeader(String url) {
        String[] splitContentType = url.split("\\.");
        String fileName = splitContentType[splitContentType.length - 1];
        String upperFileName = fileName.toUpperCase();

        return ContentType.valueOf(upperFileName).getTypeMessage();
    }
}
