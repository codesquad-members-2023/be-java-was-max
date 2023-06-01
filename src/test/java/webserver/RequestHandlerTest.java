package webserver;

import cafe.app.user.entity.User;
import cafe.app.user.repository.MemoryUserRepository;
import cafe.app.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.RequestHandler;
import webserver.http.common.ContentType;
import webserver.http.parser.HttpResponseParser;
import webserver.http.response.HttpResponse;
import webserver.http.session.SessionContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static webserver.http.common.header.EntityHeaderType.CONTENT_TYPE;
import static webserver.http.common.header.ResponseHeaderType.SET_COOKIE;

class RequestHandlerTest {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerTest.class);
    private static final int DEFAULT_PORT = 8080;

    private Thread createWebServerThread() {
        return createWebServerThread(1);
    }

    private Thread createWebServerThread(int limit) {
        return new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
                Socket connection;
                int count = 0;
                while (count < limit) {
                    if ((connection = serverSocket.accept()) != null) {
                        Thread thread = new Thread(new RequestHandler(connection));
                        thread.start();
                    }
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @AfterEach
    public void clean() {
        logger.debug("call clean");
        new MemoryUserRepository().deleteAll();
    }

    @Nested
    @DisplayName("회원가입과 로그인")
    class WhenSignUpAndLogin {

        @Test
        @DisplayName("POST 방식으로 데이터를 전달하여 회원가입한다")
        void signup() throws IOException {
            // given
            Thread serverThread = createWebServerThread();
            CompletableFuture<Void> serverThreadFuture = CompletableFuture.runAsync(serverThread);

            Socket socket = new Socket("localhost", 8080);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String requestLine = "POST /users HTTP/1.1";
            String host = "Host: localhost:8080";
            String connection = "Connection: keep-alive";
            String contentLength = "Content-Length: 59";
            String contentType = "Content-Type: application/x-www-form-urlencoded";
            String accept = "Accept: */*";
            String eof = "";
            String messageBody = "userId=user1&password=user1user1&name=%EA%B9%80%EC%9A%A9%ED%99%98&email=user1%40naver.com";
            String requestString = String.join("\r\n", requestLine, host, connection, contentLength, contentType,
                    accept, eof, messageBody);

            // when
            writer.println(requestString);
            // then
            UserRepository userRepository = new MemoryUserRepository();
            serverThreadFuture.thenRun(() -> {
                User user = userRepository.findByUserId("user1")
                        .orElseThrow();
                assertThat(user).isNotNull();
                assertThat(user.getName()).isEqualTo("김용환");
            });

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            HttpResponse response = HttpResponseParser.parse(br);
            assertThat(response.getStatusLine()
                    .toString()).isEqualTo("HTTP/1.1 302 FOUND");

            serverThreadFuture.join();
        }

        @Test
        @DisplayName("아이디와 패스워드가 주어지고 로그인을 요청하고 로그인에 성공하면 쿠키 값에 세션ID를 응답받고 index.html 페이지로 리다이렉션한다")
        public void login() throws IOException, InterruptedException {
            // given
            MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
            memoryUserRepository.save(User.builder()
                    .userId("user1")
                    .password("user1user1")
                    .name("김용환")
                    .email("user1@gmail.com")
                    .build());
            Thread serverThread = createWebServerThread();
            serverThread.start();
            // when
            Socket socket = new Socket("localhost", 8080);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String requestLine = "POST /login HTTP/1.1";
            String host = "Host: localhost:8080";
            String connection = "Connection: keep-alive";
            String contentLength = "Content-Length: " + "userId=user1&password=user1user1".length();
            String eof = "";
            String messageBody = "userId=user1&password=user1user1";
            String requestHeader = String.join("\r\n", requestLine, host, connection, contentLength, eof, messageBody);
            writer.println(requestHeader);
            // then
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            HttpResponse response = HttpResponseParser.parse(br);
            assertThat(response.getStatusLine()
                    .toString()).isEqualTo("HTTP/1.1 302 FOUND");
            assertThat(response.getResponseHeader().get(SET_COOKIE).isPresent()).isTrue();
            assertThat(response.getResponseHeader().get(SET_COOKIE).orElseThrow()).matches("sid=\\d{16};Path=/");
            logger.debug("response : {}", response);
            //cleanup
            socket.close();
            serverThread.join();
        }

        @Test
        @DisplayName("로그인 이후에 서버에 세션 아이디 정보를 쿠키를 통해서 전달했을 때 서버에서 Request의 세션 아이디를 통해서 기존 세션을 반환한다")
        public void responseExistSessionAfterLogin() throws IOException, InterruptedException {
            // given
            MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
            // 회원 미리 추가
            memoryUserRepository.save(User.builder()
                    .userId("user1")
                    .password("user1user1")
                    .name("김용환")
                    .email("user1@gmail.com")
                    .build());
            Thread serverThread = createWebServerThread(2);
            serverThread.start();

            // 로그인
            Socket socket = new Socket("localhost", 8080);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String requestLine = "POST /login HTTP/1.1";
            String host = "Host: localhost:8080";
            String connection = "Connection: keep-alive";
            String accept = "Accept: */*";
            String contentLength = "Content-Length: " + "userId=user1&password=user1user1".length();
            String eof = "";
            String messageBody = "userId=user1&password=user1user1";
            String requestHeader = String.join("\r\n", requestLine, host, connection, accept, contentLength, eof,
                    messageBody);
            writer.println(requestHeader);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            HttpResponse response = HttpResponseParser.parse(br);
            String sid = response.getResponseHeader().get(SET_COOKIE).orElseThrow().split(";")[0].split("=")[1];
            socket.close();

            socket = new Socket("localhost", 8080);
            writer = new PrintWriter(socket.getOutputStream(), true);
            requestLine = "GET / HTTP/1.1";
            String cookie = String.format("Cookie: sid=%s", sid);
            requestHeader = String.join("\r\n", requestLine, host, connection, accept, cookie, eof);
            writer.println(requestHeader);
            // when
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = HttpResponseParser.parse(br);
            // then
            Assertions.assertThat(response.getResponseHeader().get(SET_COOKIE).orElse(null))
                    .isEqualTo(String.format("sid=%s;Path=/", sid));
            //cleanup
            socket.close();
            serverThread.join();
        }

        @Test
        @DisplayName("로그인된 상태에서 로그아웃을 요청한다")
        public void logout() throws IOException, InterruptedException {
            // given
            MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
            // 회원 미리 추가
            memoryUserRepository.save(User.builder()
                    .userId("user1")
                    .password("user1user1")
                    .name("김용환")
                    .email("user1@gmail.com")
                    .build());
            Thread serverThread = createWebServerThread(2);
            serverThread.start();

            // 로그인
            Socket socket = new Socket("localhost", 8080);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String requestLine = "POST /login HTTP/1.1";
            String host = "Host: localhost:8080";
            String connection = "Connection: keep-alive";
            String accept = "Accept: */*";
            String contentLength = "Content-Length: " + "userId=user1&password=user1user1".length();
            String eof = "";
            String messageBody = "userId=user1&password=user1user1";
            String requestHeader = String.join("\r\n", requestLine, host, connection, accept, contentLength, eof,
                    messageBody);
            writer.println(requestHeader);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            HttpResponse response = HttpResponseParser.parse(br);
            String sid = response.getResponseHeader().get(SET_COOKIE).orElseThrow().split(";")[0].split("=")[1];
            socket.close();

            //when
            socket = new Socket("localhost", 8080);
            writer = new PrintWriter(socket.getOutputStream(), true);
            requestLine = "POST /logout HTTP/1.1";
            String cookie = String.format("Cookie: sid=%s", sid);
            requestHeader = String.join("\r\n", requestLine, host, connection, accept, cookie, eof);
            writer.println(requestHeader);

            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(SessionContainer.containsHttpSession(sid)).isFalse();
            assertions.assertThat(response.getResponseHeader().get(SET_COOKIE).orElse(null)).isEqualTo(null);
            //cleanup
            socket.close();
            serverThread.join();
        }
    }

    @Nested
    @DisplayName("정적 리소스를 요청하는 경우")
    class WhenStatisResource {

        @Test
        @DisplayName("salmple.html 요청")
        void testSampleHtml() throws IOException, InterruptedException {
            //given
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
            softAssertions.assertThat(response.get(CONTENT_TYPE))
                    .isEqualTo(ContentType.HTML);
            softAssertions.assertThat(response.get(CONTENT_LENGTH))
                    .isEqualTo("Hello World\r\n".getBytes().length);
            softAssertions.assertThat(response.getMessageBody())
                    .isEqualTo("Hello World\r\n".getBytes());

            //cleanup
            socket.close();
            serverThread.join();
        }

        @Test
        @DisplayName("style.css 요청")
        void testStyleCss() throws IOException, InterruptedException {
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
    }

    @Nested
    @DisplayName("동적 리소스를 요청하는 경우")
    class WhenDynamicResource {

        @Test
        @DisplayName("로그인 후에 로그인 버튼은 표시되지 않고, 로그아웃 버튼이 표시된다.")
        public void responseExistSessionAfterLogin() throws IOException, InterruptedException {
            // given
            MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
            // 회원 미리 추가
            memoryUserRepository.save(User.builder()
                    .userId("user1")
                    .password("user1user1")
                    .name("김용환")
                    .email("user1@gmail.com")
                    .build());
            Thread serverThread = createWebServerThread(2);
            serverThread.start();

            // 로그인
            Socket socket = new Socket("localhost", 8080);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String requestLine = "POST /login HTTP/1.1";
            String host = "Host: localhost:8080";
            String connection = "Connection: keep-alive";
            String accept = "Accept: */*";
            String contentLength = "Content-Length: " + "userId=user1&password=user1user1".length();
            String eof = "";
            String messageBody = "userId=user1&password=user1user1";
            String requestHeader = String.join("\r\n", requestLine, host, connection, accept, contentLength, eof,
                    messageBody);
            writer.println(requestHeader);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            HttpResponse response = HttpResponseParser.parse(br);
            String sid = response.getResponseHeader().get(SET_COOKIE).orElseThrow().split(";")[0].split("=")[1];
            socket.close();

            socket = new Socket("localhost", 8080);
            writer = new PrintWriter(socket.getOutputStream(), true);
            requestLine = "GET / HTTP/1.1";
            String cookie = String.format("Cookie: sid=%s", sid);
            requestHeader = String.join("\r\n", requestLine, host, connection, accept, cookie, eof);
            writer.println(requestHeader);
            // when
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = HttpResponseParser.parse(br);
            // then
            messageBody = new String(response.getMessageBody());
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(messageBody).doesNotContain("<li><a href=\"login\" role=\"button\">로그인</a></li>");
            assertions.assertThat(messageBody).contains("<a href=\"logout\" role=\"button\">로그아웃</a>");
            //cleanup
            socket.close();
            serverThread.join();
        }
    }
}
