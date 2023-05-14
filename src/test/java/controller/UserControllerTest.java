package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserControllerTest {
    UserController userController;

    @BeforeEach
    void setUp() {
        this.userController = new UserController();
    }

    @Test
    @DisplayName("URL이 '/'인 경우 'index.html'을 반환한다")
    void requestMapping() {
        String startLine = "GET / HTTP/1.1";
        HttpRequest request = new HttpRequest(startLine);

        assertThat(userController.requestMapping(request)).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("회원가입 요청 처리 후 '/' 페이지로 redirect 한다")
    void signUp() {
        String startLine = "GET /user/create?userId=yeon&password=1234&name=yeon&email=yeonise%40code.com HTTP/1.1";
        HttpRequest request = new HttpRequest(startLine);

        assertThat(userController.requestMapping(request)).isEqualTo("redirect:/");
    }

    @Test
    @DisplayName("요청에 따른 URL을 반환한다")
    void getUrl() {
        String startLine = "GET /user/login.html HTTP/1.1";
        HttpRequest request = new HttpRequest(startLine);

        assertThat(userController.requestMapping(request)).isEqualTo("/user/login.html");
    }
}
