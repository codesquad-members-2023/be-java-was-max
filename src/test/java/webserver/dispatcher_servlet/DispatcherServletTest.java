package webserver.dispatcher_servlet;

import static http.common.HttpMethod.GET;
import static http.common.HttpVersion.HTTP_1_0;

import cafe.app.user.controller.dto.UserResponse;
import cafe.app.user.repository.MemoryUserRepository;
import cafe.app.user.service.UserService;
import cafe.app.user.validator.UserValidator;
import http.request.HttpServletRequest;
import http.request.component.RequestHeader;
import http.request.component.RequestLine;
import http.request.component.RequestParameter;
import http.request.component.RequestURI;
import http.response.HttpServletResponse;
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
        HashMap<String, String> parameter = new HashMap<>();
        parameter.put("userId", "yonghwan1107");
        parameter.put("password", "yonghwan1107");

        HttpServletRequest request = new HttpServletRequest(
            new RequestLine(GET,
                new RequestURI("/user/create",
                    new RequestParameter(parameter)),
                HTTP_1_0),
            new RequestHeader(new HashMap<>()));
        HttpServletResponse response = new HttpServletResponse();
        // when
        dispatcherServlet.doDispatch(request, response);
        // then
        UserResponse userResponse = new UserService(new MemoryUserRepository(), new UserValidator())
            .findUser("yonghwan1107");
        Assertions.assertThat(userResponse).isNotNull();
    }
}
