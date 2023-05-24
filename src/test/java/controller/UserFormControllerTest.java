package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servlet.controller.UserFormController;
import webserver.util.HttpResponseUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserFormControllerTest {
    @Test
    @DisplayName("/user/form.html 요청이 들어오면 해당 컨트롤러를 호출하고, user/form의 viewName을 반환한다.)")
    void process() {
        UserFormController userFormController = new UserFormController();

        String viewName = userFormController.process(null, new HttpResponseUtils());

        assertThat("user/form").isEqualTo(viewName);
    }
}
