package container;

import container.domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private ResponseHandler() {
    }

    public static void response(HttpResponse httpResponse, Socket connection) {
        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            httpResponse.written(out);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
