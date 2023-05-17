package was.request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.container.FrontServlet;
import was.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATIC_PRIFIX = "/static";

    private final BufferedReader reader;
    private final DataOutputStream writer;

    public RequestHandler(InputStream in, OutputStream out) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.writer = new DataOutputStream(out);
    }

    public void run() {
        try (reader; writer) {
            LOGGER.info("HTTP REQUEST PARSING START");
            final HttpRequest request = HttpRequest.from(reader);

            LOGGER.info(request.toString());
            LOGGER.info("HTTP REQUEST PARSING COMPLETE");

            handle(request);

            LOGGER.info("HTTP RESPONSE COMPLETE");
        } catch (IOException | IllegalArgumentException ex){
            LOGGER.error(ex.getMessage());
        }
    }

    private void handle(final HttpRequest request) {
        final String requestUrl = request.getUrl();   // .html

        var contentType = ContentType.from(requestUrl);

        if (contentType.isEmpty()) {

            forwardRequest(request);
            return;
        }

        sendStaticResource(requestUrl, contentType.get());
    }

    private void sendStaticResource(final String requestPath, final ContentType contentType) {
        //TODO
        // html, css, ico 등에 따라 처리 필요
        try {
            final byte[] body = Files.readAllBytes(findStaticPath(requestPath));
            response200Header(contentType.getMimeType(), body.length);
            writer.write(body);
            writer.flush();
        } catch (IOException | URISyntaxException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void forwardRequest(final HttpRequest request) {
        final FrontServlet frontServlet = new FrontServlet();

        frontServlet.init();

        frontServlet.service(request, new HttpResponse());
    }

    private Path findStaticPath(String requestPath) throws URISyntaxException {
        LOGGER.info("REQUEST PATH = " + requestPath);
        final URL url = getClass().getResource(STATIC_PRIFIX + requestPath);

        // 404 처리?
        if (url == null) {
            throw new IllegalArgumentException("해당 리소스가 없습니다.");
        }

        return Path.of(url.toURI());
    }

    private void response200Header(String mimeType, int contentsLength) {
        try {
            writer.write("HTTP/1.1 200 OK \r\n".getBytes());
            writer.write(("Content-Type: " + mimeType + "\r\n").getBytes());
            writer.write(("Content-Length: " + contentsLength + "\r\n").getBytes());
            writer.write("\r\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
