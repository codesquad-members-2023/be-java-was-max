package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servlet.controller.LoginFormController;
import webserver.util.HttpResponseUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginFormControllerTest {
    @Test
    @DisplayName("/user/login.html 요청이 들어오면 해당 컨트롤러를 호출하고, 그 viewName을 반환한다.")
    void process() {
        LoginFormController loginFormController = new LoginFormController();

        String viewName = loginFormController.process(null, new HttpResponseUtils());

        assertThat("user/login").isEqualTo(viewName);
    }
}
