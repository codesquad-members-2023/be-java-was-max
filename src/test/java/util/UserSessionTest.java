package util;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.util.UserSession;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserSessionTest {
    private static final String COOKIE_NAME = "sid";

    @Test
    @DisplayName("uuid로 생성한 sessionID를 key로, 현재 로그인한 User 객체를 value로 하여 UserSession에 저장한다.")
    void sessionSaveAndGet() {
        String sessionId = UUID.randomUUID().toString();
        User user = new User("sully1234", "1234", "sully", "sully@naver.com");
        UserSession.addUser(sessionId, user);

        Map<String, String> cookies = Map.of(COOKIE_NAME, sessionId);
        User sessionUser = UserSession.get(cookies);

        assertThat(sessionUser).isEqualTo(user);
    }
}
