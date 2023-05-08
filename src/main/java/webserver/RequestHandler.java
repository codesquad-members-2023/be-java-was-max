package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            /**
             * Request 처리
             * */
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            // Request Line을 읽는다
            String line = inputReader.readLine();
            logger.debug("request line : {}", line);

            // Request Line에서 경로를 추출한다
            // TODO 메서드 추출
            String[] requestHeader = line.split(" ");
            String path = requestHeader[1];

            // Request Message를 읽고 로그를 출력한다
            while (!line.equals("")) {
                line = inputReader.readLine();
                logger.debug("header : {}", line);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            logger.info("body to String : {}" + body.toString());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
