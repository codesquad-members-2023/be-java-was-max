package http;

import model.Body;
import model.Request;
import model.RequestHeaders;
import model.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class RequestMaker {

    private static final Logger logger = LoggerFactory.getLogger(RequestMaker.class);

    private BufferedReader br;

    public RequestMaker(InputStream is) throws UnsupportedEncodingException {
        this.br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }

    public Request make() throws IOException {
        RequestLine requestLine = makeRequestLine();
        RequestHeaders requestHeaders = makeHeaders();
        Body body = null;

        if (requestLine.hasBody()) {
            int contentLength = Integer.parseInt(requestHeaders.getHeaders().get("Content-Length"));
            body = makeBody(contentLength);
        }
        return new Request(requestLine, requestHeaders, body);
    }

    private RequestLine makeRequestLine() throws IOException {
        String rawRequestLine = br.readLine().trim();
        logger.debug("RequestLine: {}", rawRequestLine);

        return new RequestLine(rawRequestLine);
    }

    private RequestHeaders makeHeaders() throws  IOException {
        String input = null;
        RequestHeaders requestHeaders = new RequestHeaders();

        while (!(input = br.readLine().trim()).equals("")) {
            requestHeaders.add(input);
        }
        return requestHeaders;
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
