package webserver;

import static http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static http.common.header.EntityHeaderType.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

import cafe.app.user.entity.User;
import cafe.app.user.repository.MemoryUserRepository;
import cafe.app.user.repository.UserRepository;
import http.response.HttpResponse;
import http.response.component.ContentType;
import http.response.parser.HttpResponseParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.RequestHandler;

class RequestHandlerTest {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerTest.class);
    private static final int DEFAULT_PORT = 8080;

    @Test
    @DisplayName("sample.html 요청시 html 파일 내용을 응답받는다.")
    void requestGet() throws IOException, InterruptedException {
        Thread serverThread = createWebServerThread();
        serverThread.start();

        // when
        Socket socket = new Socket("localhost", 8080);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        String requestLine = "GET /sample.html HTTP/1.0";
        String host = "Host: localhost:8080";
        String accept = "Accept: text/html";
        String eof = "";
        String requestHeader = String.join("\r\n", requestLine, host, accept, eof);
        writer.println(requestHeader);
        // then
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        HttpResponse response = HttpResponseParser.parse(br);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(response.getStatusLine())
            .isEqualTo("HTTP/1.1 200 OK");
        softAssertions.assertThat(response.getHeaderValue(CONTENT_TYPE))
            .isEqualTo(ContentType.HTML);
        softAssertions.assertThat(response.getHeaderValue(CONTENT_LENGTH))
            .isEqualTo("Hello World\r\n".getBytes().length);
        softAssertions.assertThat(response.getMessageBody())
            .isEqualTo("Hello World\r\n".getBytes());

        //cleanup
        socket.close();
        serverThread.join();
    }

    @Test
    @DisplayName("POST 방식으로 데이터를 전달하여 회원가입한다")
    void signup() throws IOException {
        // given
        Thread serverThread = createWebServerThread();
        CompletableFuture<Void> serverThreadFuture = CompletableFuture.runAsync(serverThread);

        Socket socket = new Socket("localhost", 8080);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        String requestLine = "POST /user/create HTTP/1.1";
        String host = "Host: localhost:8080";
        String connection = "Connection: keep-alive";
        String contentLength = "Content-Length: 59";
        String contentType = "Content-Type: application/x-www-form-urlencoded";
        String accept = "Accept: */*";
        String eof = "";
        String messageBody = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        String requestString = String.join("\r\n", requestLine, host, connection, contentLength, contentType, accept,
            eof, messageBody);

        // when
        writer.println(requestString);
        // then
        UserRepository userRepository = new MemoryUserRepository();
        serverThreadFuture.thenRun(() -> {
            User user = userRepository.findByUserId("javajigi")
                .orElseThrow();
            assertThat(user).isNotNull();
        });

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        HttpResponse response = HttpResponseParser.parse(br);
        assertThat(response.getStatusLine()
            .toString()).isEqualTo("HTTP/1.1 302 FOUND");

        //cleanup
        socket.close();
        serverThreadFuture.join();
    }

    @Test
    @DisplayName("style.css 파일을 요청했을때 응답한다")
    void contentType() throws IOException, InterruptedException {
        // given
        Thread serverThread = createWebServerThread();
        serverThread.start();
        // when
        Socket socket = new Socket("localhost", 8080);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        String requestLine = "GET ./css/styles.css HTTP/1.1";
        String host = "Host: localhost:8080";
        String connection = "Connection: keep-alive";
        String accept = "Accept: text/css,*/*;q=0.1";
        String eof = "";

        String requestHeader = String.join("\r\n", requestLine, host, connection, accept, eof);
        writer.println(requestHeader);

        // then
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        HttpResponse response = HttpResponseParser.parse(br);
        assertThat(response.getStatusLine()
            .toString()).isEqualTo("HTTP/1.1 200 OK");

        //cleanup
        socket.close();
        serverThread.join();
    }

    private Thread createWebServerThread() {
        return new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
                Thread thread = new Thread(new RequestHandler(serverSocket.accept()));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
