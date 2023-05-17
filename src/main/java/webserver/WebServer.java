package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080; // 기본 포트 값 설정

    public static void main(String[] args) throws Exception {
        int port;
        if (args == null || args.length == 0) { // 인자가 없는 경우
            port = DEFAULT_PORT; // DEFAULT_PORT 상수 값을 port에 대입
        } else { // 인자가 하나 이상 있는 경우
            port = Integer.parseInt(args[0]); // args[0]에 저장된 값을 Integer.parseInt() 메서드를 이용하여 정수형으로 변환한 뒤 port 변수에 대입
        }

        // 서버 소켓을 생성
        // 웹 서버는 기본적으로 8080 포트를 사용
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            LOGGER.info("Web Application Server started {} port.", port);

            // 클라이언트와 연결될 때까지 대기
            Socket connection;
            while ((connection = listenSocket.accept()) != null) { // accept() 메서드를 이용하여 클라이언트의 연결 요청을 대기하다가 연결 요청이 들어오면 연결을 수락하고 클라이언트와 통신할 소켓인 connection 객체를 생성
                // accept() 메서드를 호출하는 부분에서 Blocking 상태가 되며 클라이언트의 연결 요청이 들어오기 전까지 대기
                Thread thread = new Thread(new RequestHandler(connection)); // connection 객체를 이용하여 새로운 RequestHandler 객체를 생성한 뒤 새로운 스레드에게 할당
                thread.start();
            }
        }
    }
}
