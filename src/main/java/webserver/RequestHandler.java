package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            if(line == null){  // 'null 발생 시' 무한루프 방지
                return;
            }
            logger.debug("request line: {}", line);
//            System.out.println("request : " + line); // favicon까지 2번 request    // 자세히 보니 로거하고 겹치는 듯한

            String[] splited = line.split(" ");
            String path = splited[1];  // GET /index.html HTTP1.1
            logger.debug("request path: {}", path);

            if (path.startsWith("/user/create")) {
                String[] cutQuestionMark = path.split("//?");
                String userInformationsAndMarkAll= cutQuestionMark[2];
                String[] cutAndMark = userInformationsAndMarkAll.split("&");
                String[] userId = cutAndMark[0].split("=");
                String[] password = cutAndMark[1].split("=");
                String[] name = cutAndMark[2].split("=");
                String[] emailRaw = cutAndMark[3].split("=");
                String[] email = emailRaw[1].split("%");
                User user = new User(userId[1], password[1], name[1], email[0]);
                Database.addUser(user);
                logger.debug(user.toString());
                return;
            }

            while(!line.equals("")) {
                logger.debug("header: {}", line);
                line = br.readLine();   // line을 br.readLine()에 넣어줘야 빈 공백의 문자열을 만날 수 있다
            }

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + path).toPath());
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
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
