package controller;

import db.Database;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servlet.controller.UserSaveController;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserSaveControllerTest {
    @AfterEach
    void clearDataBase() {
        Database.deleteAll();
    }

    @Test
    @DisplayName("/user/create 요청이 들어오면 해당 컨트롤러를 호출하여 User를 생성하고 redirect:/ viewName을 반환한다.")
    void process() {
        UserSaveController userSaveController = new UserSaveController();

        String viewName = userSaveController.process(Map.of("userId", "sully1234", "password", "1234", "name", "sully", "email", "123%40123"));

        assertThat("redirect:/").isEqualTo(viewName);

        Collection<User> users = Database.findAll();
        User findUser = Database.findUserById("sully1234");


        // 실패하더라도 끝까지 검증
        assertAll(
                () -> assertThat(users.size()).isEqualTo(1),
                () -> assertThat(findUser.getUserId()).isEqualTo("sully1234"),
                () -> assertThat(findUser.getPassword()).isEqualTo("1234"),
                () -> assertThat(findUser.getName()).isEqualTo("sully"),
                () -> assertThat(findUser.getEmail()).isEqualTo("123%40123")
        );
    }


}
