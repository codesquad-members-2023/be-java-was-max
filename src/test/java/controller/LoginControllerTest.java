package controller;

import db.Database;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servlet.controller.LoginController;
import webserver.util.HttpResponseUtils;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginControllerTest {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";

    @Test
    @DisplayName("해당 컨트롤러는 파라미터로 넘어온 id, password와 DB의 id, password를 비교하여, 로그인이 성공하면 redirect:/를 viewName으로 반환한다.")
    void loginSuccess() {
        LoginController loginController = new LoginController();

        Database.addUser(new User("sully", "1234", "설리", "sully@naver.com"));

        String viewName = loginController.process(Map.of(USER_ID, "sully", PASSWORD, "1234"), new HttpResponseUtils());
        assertThat(viewName).isEqualTo("redirect:/");
    }

    @Test
    @DisplayName("해당 컨트롤러는 파라미터로 넘어온 id, password와 DB의 id, password를 비교하여, 로그인이 실패하면 user/login_failed를 viewName으로 반환한다.")
    void loginFail() {
        LoginController loginController = new LoginController();

        Database.addUser(new User("sully", "1234", "설리", "sully@naver.com"));

        String viewName = loginController.process(Map.of(USER_ID, "sulla", PASSWORD, "4321"), new HttpResponseUtils());
        assertThat(viewName).isEqualTo("user/login_failed");
    }

    /**
     * 이거 안 해주면 expected: 2 오류남
     */
    @AfterEach
    void clearDatabase() {
        Database.deleteAll();
    }
}
