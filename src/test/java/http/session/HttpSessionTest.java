package http.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.common.HttpMethod;
import webserver.http.common.version.HttpVersion;
import webserver.http.request.HttpRequest;
import webserver.http.request.component.*;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionContainer;

import java.util.HashMap;

import static webserver.http.common.HttpMethod.GET;
import static webserver.http.common.HttpMethod.POST;
import static webserver.http.common.header.RequestHeaderType.COOKIE;

class HttpSessionTest {

    private HttpRequest request;

    @BeforeEach
    public void setup() {
        request = createHttpRequest(POST, "/login");
    }

    private HttpRequest createHttpRequest(HttpMethod method, String path) {
        RequestLine requestLine = new RequestLine(method, new RequestURI(path), new HttpVersion(1, 1));
        RequestHeader requestHeader = new RequestHeader(new HashMap<>());
        RequestQueryString queryString = new RequestQueryString(new HashMap<>());
        RequestMessageBody requestMessageBody = new RequestMessageBody(new HashMap<>());
        return new HttpRequest(requestLine, requestHeader, queryString, requestMessageBody, null);
    }

    @Test
    @DisplayName("HttpRequest가 주어지고 HttpRequest가 세션을 처음 요청할때 SessionContainer에서 HttpSession을 만들어 반환한다")
    public void test1() {
        // given

        // when
        HttpSession httpSession = request.getHttpSession();
        // then
        // 세션이 생성되었는지 확인
        Assertions.assertThat(httpSession).isNotNull();
        // 세션 컨테이너안에 세션 아이디에 따른 방금 할당받은 세션이 있는지 확인
        Assertions.assertThat(httpSession).isSameAs(SessionContainer.getSession(httpSession.getId()));
    }

    @Test
    @DisplayName("HttpRequest에 Cookie 헤더에 세션 아이디를 넣어 요청시 세션을 새로 생성하지 않고 저장되어 있는걸 반환한다")
    public void test2() {
        // given
        HttpSession httpSession = request.getHttpSession();
        String sessionId = httpSession.getId();
        HttpRequest otherHttpRequest = createHttpRequest(GET, "/");
        String cookieString = String.format("sid=%s; Path=/", sessionId);
        otherHttpRequest.getRequestHeader().put(COOKIE, cookieString);
        // when
        HttpSession actual = otherHttpRequest.getHttpSession();
        // then
        Assertions.assertThat(actual).isSameAs(httpSession);
    }
}
