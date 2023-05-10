package webserver.handler;

import static java.nio.charset.StandardCharsets.UTF_8;
import static webserver.request.common.HttpMethod.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.common.ContentType;
import webserver.request.HttpRequest;
import webserver.request.factory.HttpRequestFactory;
import webserver.request.component.RequestHeader;
import webserver.request.component.RequestLine;
import webserver.request.component.RequestParameter;
import webserver.request.component.RequestURI;
import webserver.response.HttpVersion;
import webserver.response.Response;
import webserver.response.ResponseFactory;
import webserver.util.RequestHandlerUtil;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(),
            connection.getPort());

        try (BufferedReader br =
            new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF_8));
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            HttpRequest httpRequest = HttpRequestFactory.create(br);
            byte[] messageBody = handleHttpRequest(httpRequest);

            ContentType contentType = ContentType.of(httpRequest.getPath());
            Response response = ResponseFactory.ok(messageBody);
            response.addHeader("Content-Type", contentType);
            response.addHeader("Content-Length", String.valueOf(response.getMessageBody().length));

            response200Header(dos, response);
            responseBody(dos, response);

            logger.debug("httpRequest : {}", httpRequest);
            logger.debug("httpResponse : {}", response);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] handleHttpRequest(HttpRequest httpRequest) throws URISyntaxException {
        String path = httpRequest.getPath();
        RequestParameter parameter = httpRequest.getParameter();

        if (RequestHandlerUtil.isStaticResource(path)) {
            return RequestHandlerUtil.readFile(path);
        } else if (path.equals("/user/create")) {
            RequestHandlerUtil.requestSignUp(parameter);
            RequestParameter requestParameter = new RequestParameter(new HashMap<>());
            RequestURI requestURI = new RequestURI("/user/login.html", requestParameter);
            RequestLine requestLine = new RequestLine(GET, requestURI, new HttpVersion(1.0));
            httpRequest = new HttpRequest(requestLine, new RequestHeader(new HashMap<>()));
            path = httpRequest.getPath();
            return RequestHandlerUtil.readFile(path);
        }

        return new byte[0];
    }

    private void response200Header(DataOutputStream dos, Response response) {
        try {
            dos.writeBytes(response.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, Response response) {
        try {
            byte[] messageBody = response.getMessageBody();
            dos.write(messageBody, 0, messageBody.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
