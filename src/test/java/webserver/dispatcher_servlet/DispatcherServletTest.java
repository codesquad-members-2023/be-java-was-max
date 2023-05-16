package webserver.dispatcher_servlet;

import static http.common.HttpMethod.POST;

import cafe.app.user.controller.dto.UserResponse;
import cafe.app.user.repository.MemoryUserRepository;
import cafe.app.user.service.UserService;
import cafe.app.user.validator.UserValidator;
import http.common.version.HttpVersion;
import http.request.HttpRequest;
import http.request.component.RequestHeader;
import http.request.component.RequestLine;
import http.request.component.RequestMessageBody;
import http.request.component.RequestParameter;
import http.request.component.RequestURI;
import http.response.HttpResponse;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DispatcherServletTest {

    @Test
    @DisplayName("dispatcher-servlet에게 회원가입 요청을 한다")
    public void signUp() throws Exception {
        // given
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        HashMap<String, String> queryString = new HashMap<>();
        RequestLine requestLine = new RequestLine(POST,
            new RequestURI("/user/create", new RequestParameter(queryString)),
            new HttpVersion(1, 1));
        RequestMessageBody messageBody = new RequestMessageBody(new HashMap<>());
        messageBody.put("userId", "yonghwan1107");
        messageBody.put("password", "yonghwan1107");
        messageBody.put("name", "김용환");
        messageBody.put("email", "yonghwan1107@gamil.com");

        HttpRequest request = new HttpRequest(requestLine,
            new RequestHeader(new HashMap<>()), messageBody);
        HttpResponse response = new HttpResponse();
        // when
        dispatcherServlet.doDispatch(request, response);
        // then
        UserResponse userResponse = new UserService(new MemoryUserRepository(),
            new UserValidator()).findUser("yonghwan1107");
        Assertions.assertThat(userResponse)
            .isNotNull();
    }
}
