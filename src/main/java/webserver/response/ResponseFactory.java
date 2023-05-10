package webserver.response;

import static webserver.response.StatusCode.*;

import java.util.HashMap;

public class ResponseFactory {

    private static final double DEFAULT_HTTP_VERSION = 1.1;

    private ResponseFactory() {

    }

    public static Response ok(byte[] messageBody) {
        return new Response(new StatusLine(
            new HttpVersion(DEFAULT_HTTP_VERSION), OK),
            new ResponseHeader(new HashMap<>()),
            messageBody);
    }
}
