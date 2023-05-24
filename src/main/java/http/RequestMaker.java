package http;

import model.Body;
import model.Headers;
import model.Request;
import model.RequestLine;

import java.io.*;

public class RequestMaker {

    private BufferedReader br;

    public RequestMaker(InputStream is) throws UnsupportedEncodingException {
        this.br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }

    public Request make() throws IOException {
        RequestLine requestLine = makeRequestLine();
        Headers header = makeHeaders();
        Body body = null;

        if (requestLine.hasBody()) {
            int contentLength = Integer.parseInt(header.getHeaders().get("Content-Length"));
            body = makeBody(contentLength);
        }
        return new Request(requestLine, header, body);
    }

    private RequestLine makeRequestLine() throws IOException {
        String rawRequestLine = br.readLine().trim();

        return new RequestLine(rawRequestLine);
    }

    private Headers makeHeaders() throws  IOException {
        String input = null;
        Headers headers = new Headers();

        while (!(input = br.readLine().trim()).equals("")) {
            headers.add(input);
        }
        return headers;
    }

    private Body makeBody(int length) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int input = br.read();
            sb.append((char) input);
        }
        return new Body(sb.toString());
    }


}
