package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.DispatcherServlet;
import webserver.util.HttpRequestUtils;
import webserver.util.HttpResponseUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // https://docs.oracle.com/javase/8/docs/api/java/nio/charset/StandardCharsets.html
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequestUtils httpRequest = RequestSeparator.askHttpRequest(br);
            HttpResponseUtils httpResponse = new HttpResponseUtils();

            String viewName = DispatcherServlet.service(httpRequest, httpResponse);             // 컨트롤러가 반환한 viewName
            String path = resolveView(viewName, httpRequest);

            httpResponse.setContent(path, httpRequest);
            sendResponseMessage(out, httpResponse);

        } catch (IOException | RuntimeException e) {
            log.error(e.getMessage());
            // TODO: 500 에러 처리
            /*
            try {
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            */
        }
    }

    private static String resolveView(String viewName, HttpRequestUtils httpRequest) {
        return httpRequest.getPath(viewName);
    }

    private static void sendResponseMessage(OutputStream out, HttpResponseUtils httpResponse) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.write(httpResponse.toBytes());
        dos.write(httpResponse.getMessageBody());
    }
}
