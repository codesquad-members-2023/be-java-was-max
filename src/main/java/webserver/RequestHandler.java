package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        InetAddress inetAddress = connection.getInetAddress();
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", inetAddress,
                connection.getPort());

        try (InputStream in = connection.getInputStream()) {
            HttpRequest httpRequest = HttpRequestUtils.parsingHttpRequest(in);
            MappingInfo mappingInfo = HandlerMapping.map(httpRequest);
            String result = HandlerAdapter.process(mappingInfo);
            HttpResponse httpResponse = ViewResolver.resolve(result);
            ResponseHandler.response(httpResponse, connection);
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
            ResponseHandler.response(new HttpResponse(HttpMethod.NOT_FOUND, new byte[0]), connection);
        }
    }
}
