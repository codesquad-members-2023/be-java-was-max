package auth;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Session {
    private final static Logger logger = LoggerFactory.getLogger(Session.class);
    private static final Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /*  세션 생성
     1. sessionId 생성 (임의의 랜덤 값)
     2. 세션 저장소에 sessionId와 보관할 값을 저장한다.
     3. sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
     */

    public static void createSessionId(Object value, HttpResponse response) {
        String sessionId = UUID.randomUUID().toString();
        logger.debug("sessionId: {}", sessionId);
        sessionStore.put(sessionId, value);

        Cookie mySessionCookie = new Cookie("mySessionCookie", sessionId);
        response.addResponseHeaders("Set-Cookie: sid=" + mySessionCookie.getValue() + ";Path=/");
    }

    /*
    세션 조회
     */
    public static Object getSession(HttpRequest request) {
        Cookie sessionCookie = request.getCookie();
        if (sessionCookie == null) {
            throw new RuntimeException("쿠키가 없습니다.");
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    /*
    세션 만료
     */
    public static void expire(HttpRequest request) {
        Cookie sessionCookie = request.getCookie();
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }
}
