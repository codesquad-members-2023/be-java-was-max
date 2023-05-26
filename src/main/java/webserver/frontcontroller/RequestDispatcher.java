package webserver.frontcontroller;

import static http.common.HttpStatus.FOUND;
import static http.common.HttpStatus.OK;
import static http.common.header.EntityHeaderType.CONTENT_LENGTH;
import static http.common.header.EntityHeaderType.CONTENT_TYPE;
import static http.common.header.ResponseHeaderType.LOCATION;
import static http.common.version.HttpVersion.HTTP_1_1;
import static http.response.component.ContentType.resolve;
import static java.nio.charset.StandardCharsets.UTF_8;

import cafe.app.user.controller.dto.UserResponse;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.component.ContentType;
import http.response.component.StatusLine;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);


    private final File file;
    private final String viewPath;

    public RequestDispatcher(File file, String viewPath) {
        this.file = file;
        this.viewPath = viewPath;
    }

    public void forward(HttpRequest request, HttpResponse response) {
        try {
            byte[] messageBodyBytes = Files.readAllBytes(file.toPath());
            if (request.hasHttpSession()) {
                String messageBody = new String(messageBodyBytes);
                UserResponse user = (UserResponse) request.getHttpSession().getAttribute("user");
                messageBodyBytes = processLogin(messageBody, user);
            }

            ContentType contentType = resolve(file.getPath());
            response.setStatusLine(new StatusLine(HTTP_1_1, OK));
            response.addHeader(CONTENT_TYPE, contentType.toString());
            response.addHeader(CONTENT_LENGTH, String.valueOf(messageBodyBytes.length));
            response.setMessageBody(messageBodyBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] processLogin(String messageBody, UserResponse user) {
        byte[] messageBodyBytes;
        // 사용장 이름 생성
        messageBody = messageBody.replace("<li class=\"hidden\"><a href=\"#\" role=\"button\"></a></li>",
            "<li><a href=\"#\" role=\"button\">" + user.getName() + "</a></li>");
        // 로그인 버튼 제거
        messageBody = messageBody.replace("<li><a href=\"login\" role=\"button\">로그인</a></li>",
            "<li class=\"hidden\"><a href=\"login\" role=\"button\">로그인</a></li>");
        // 로그아웃 버튼 생성
        messageBody = messageBody.replace("<li class=\"hidden\" id=\"loginList\">", "<li id=\"loginList\">");
        // 개인정보수정 버튼 생성
        messageBody = messageBody.replace("<li class=\"hidden\"><a href=\"#\" role=\"button\">개인정보수정</a></li>",
            "<li><a href=\"#\" role=\"button\">개인정보수정</a></li>");
        messageBodyBytes = messageBody.getBytes(UTF_8);
        return messageBodyBytes;
    }

    public void redirect(HttpResponse response) {
        response.addHeader(LOCATION, viewPath);
        response.setStatusLine(new StatusLine(HTTP_1_1, FOUND));
    }
}
