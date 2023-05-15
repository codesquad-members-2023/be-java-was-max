package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import model.ContentType;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Map<String, String> startLine = new HashMap<>();
    private final Map<String, String> header = new HashMap<>();
    private final URLHandler urlHandler = new URLHandler();
    private final Responsor responsor = new Responsor();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String buffer = br.readLine().trim();
            logger.debug("Start Line of Request Header : {}", buffer);
            RequestParser.parseStartLine(buffer, startLine);
            while (true) {
                buffer = br.readLine().trim();
                if (buffer.equals("")) {
                    break;
                }
                RequestParser.parseHeader(buffer, header);
                User user = urlHandler.createUser(startLine.get("URL"));
                if (user != null) {
                    logger.debug("User Created");
                }
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] response = responsor.makeResponse(startLine.get("URL"));
            dos.write(response);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
