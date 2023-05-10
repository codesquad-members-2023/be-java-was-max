package webserver;

import db.Database;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestHandler;
import webserver.util.IOutil;

class RequestHandlerTest {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerTest.class);
    private static final int DEFAULT_PORT = 8080;

    @Test
    @DisplayName("sample.html 요청시 html 파일 내용을 응답받는다.")
    public void requestGet() throws IOException, InterruptedException {
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
                Thread thread = new Thread(new RequestHandler(serverSocket.accept()));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        //when
        Socket socket = new Socket("localhost", 8080);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        String requestLine = "GET /sample.html HTTP/1.0";
        String host = "Host: localhost:8080";
        String accept = "Accept: text/html";
        String eof = "";
        String requestHeader = String.join("\r\n", requestLine, host, accept, eof);
        writer.println(requestHeader);
        //then
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = IOutil.readFromInputStream(br);
        String statusLine = "HTTP/1.1 200 OK";
        String contentType = "Content-Type: text/html;charset=utf-8";
        String contentLength = "Content-Length: 13";
        String messageBody = "Hello World";
        String expect = String.join("\r\n", statusLine, contentLength, contentType, eof,
            messageBody);
        Assertions.assertThat(response).isEqualTo(expect);

        //cleanup
        socket.close();
        serverThread.join();
    }

    @Test
    @DisplayName("GET 방식으로 데이터를 전달하여 회원가입한다")
    public void signup() throws IOException, InterruptedException {
        //given
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
                Thread thread = new Thread(new RequestHandler(serverSocket.accept()));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        CompletableFuture<Void> future = CompletableFuture.runAsync(serverThread);
        //when
        Socket socket = new Socket("localhost", 8080);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        String requestLine = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        String host = "Host: localhost:8080";
        String connection = "Connection: keep-alive";
        String accept = "Accept: */*";
        String eof = "";
        String requestHeader = String.join("\r\n", requestLine, host, connection, accept, eof);
        writer.println(requestHeader);

        //then
        future.thenRun(() -> {
            User user = Database.findUserById("javajigi");
            Assertions.assertThat(user).isNotNull();
        });
        //cleanup
        socket.close();

    }

    @Test
    @DisplayName("style.css 파일을 요청했을때 응답한다")
    public void contentType() throws IOException {
        //given
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
                Thread thread = new Thread(new RequestHandler(serverSocket.accept()));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        CompletableFuture<Void> future = CompletableFuture.runAsync(serverThread);
        //when
        Socket socket = new Socket("localhost", 8080);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        String requestLine = "GET ./css/styles.css HTTP/1.1";
        String host = "Host: localhost:8080";
        String connection = "Connection: keep-alive";
        String accept = "Accept: text/css,*/*;q=0.1";
        String eof = "";
        String requestHeader = String.join("\r\n", requestLine, host, connection, accept, eof);
        writer.println(requestHeader);

        //then
        BufferedReader br = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
        String response = IOutil.readFromInputStream(br);
        String statusLine = response.split("\r\n")[0];
        Assertions.assertThat(statusLine).isEqualTo("HTTP/1.1 200 OK");
        //cleanup
        socket.close();
    }
}
