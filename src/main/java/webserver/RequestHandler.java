package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.RequestLineParser;
import response.ResponseGenerator;
import response.ViewPathResolver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private final Socket connection;
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final ViewPathResolver viewPathResolver = new ViewPathResolver();
    private final ResponseGenerator responseGenerator = new ResponseGenerator();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // RequestLine을 읽어 인스턴스를 생성한다
            RequestLineParser requestLineParser = new RequestLineParser(inputReader.readLine());
            logger.info(requestLineParser.getHttpMethod() + requestLineParser.getRequestURI());

            String requestHeader;
            StringBuilder requestHeaderBuilder = new StringBuilder();
            while ((requestHeader = inputReader.readLine()) != null && !requestHeader.isEmpty()) {
                logger.debug("header : {}", requestHeader);
                requestHeaderBuilder.append(requestHeader).append("\r\n");
            }

            String httpMethod = requestLineParser.getHttpMethod();
            if (httpMethod.equals("GET")) {
                handleGetRequest(requestLineParser, viewPathResolver, responseGenerator, out);
            }
            if (httpMethod.equals("POST")) {
                handlePostRequest(requestLineParser, viewPathResolver, responseGenerator, out);

                // TODO : RequestBody에서 입력값 추출하기
                String requestHeaderString = requestHeaderBuilder.toString();
                int contentLength = 0;
                String contentLengthHeader = "Content-Length: ";
                for (String headerLine : requestHeaderString.split("\r\n")) {
                    if (headerLine.startsWith(contentLengthHeader)) {
                        contentLength = Integer.parseInt(headerLine.substring(contentLengthHeader.length()));
                        break;
                    }
                }

                char[] requestBody = new char[contentLength];
                int bytesRead = inputReader.read(requestBody, 0, contentLength);
                String requestBodyString = new String(requestBody, 0, bytesRead);

                logger.info("request body: {}", requestBodyString);

                // TODO User에 저장하기
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleGetRequest(RequestLineParser requestLineParser, ViewPathResolver viewPathResolver, ResponseGenerator responseGenerator, OutputStream out) throws IOException {
        String requestedURI = requestLineParser.getRequestURI();
        String mimeType = viewPathResolver.resolveMimeType(requestedURI);
        byte[] responseBody = viewPathResolver.readViewFile(requestedURI);

        DataOutputStream dos = new DataOutputStream(out);
        responseGenerator.response200Header(dos, responseBody.length, mimeType);
        responseGenerator.responseBody(dos, responseBody);
    }

    private void handlePostRequest(RequestLineParser requestLineParser, ViewPathResolver viewPathResolver, ResponseGenerator responseGenerator, OutputStream out) throws IOException {
        logger.info("POST");
        String requestedURI = requestLineParser.getRequestURI();
        String mimeType = viewPathResolver.resolveMimeType(requestedURI);
        byte[] responseBody = viewPathResolver.readViewFile(requestedURI);

        DataOutputStream dos = new DataOutputStream(out);
        responseGenerator.response302Header(dos, responseBody.length, mimeType);
        responseGenerator.responseBody(dos, responseBody);
    }
}
