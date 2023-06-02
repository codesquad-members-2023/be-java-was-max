package webserver;

import cafe.app.user.repository.MemoryUserRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.handler.RequestHandler;
import webserver.http.common.ContentType;
import webserver.http.parser.HttpResponseParser;
import webserver.http.response.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static webserver.http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static webserver.http.common.header.EntityHeaderType.CONTENT_TYPE;

class RequestHandlerTest {

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
        new MemoryUserRepository().deleteAll();
    }

    @Nested
    @DisplayName("정적 리소스를 요청하는 경우")
    class StaticResourceTest {

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
    }
}
