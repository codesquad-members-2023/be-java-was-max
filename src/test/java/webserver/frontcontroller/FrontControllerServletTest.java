package webserver.frontcontroller;

import cafe.app.user.controller.dto.UserResponse;
import cafe.app.user.entity.User;
import cafe.app.user.repository.MemoryUserRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import webserver.http.parser.HttpRequestParser;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionContainer;

import java.io.*;

import static cafe.errors.errorcode.UserErrorCode.ALREADY_EXIST_USERID;
import static webserver.http.common.HttpStatus.FOUND;
import static webserver.http.common.HttpStatus.OK;
import static webserver.http.common.header.ResponseHeaderType.LOCATION;

class FrontControllerServletTest {
    private final String testDirectory = "src/test/resources/";

    @Nested
    @DisplayName("회원 컨트롤러 테스트")
    class UserControllerTest {
        @Test
        @DisplayName("사용자가 비로그인 상태에서 회원 목록 페이지로 이동하고자 하는 경우 로그인 페이지로 이동한다.")
        public void givenNonLoginWhenAccessUserListPageThenRedirectLoginPage() throws IOException {
            // given
            InputStream in = new FileInputStream(testDirectory + "Http_Get_users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            HttpRequest request = HttpRequestParser.parseHttpRequest(reader);
            HttpResponse response = new HttpResponse();
            FrontControllerServlet frontController = new FrontControllerServlet();
            // when
            frontController.service(request, response);
            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(response.getStatusLine().getHttpStatus()).isEqualTo(FOUND);
            assertions.assertThat(response.getResponseHeader().get(LOCATION)).isEqualTo("/login");
        }

        @Test
        @DisplayName("회원가입 정보가 주어지고 사용자가 회원가입을 요청할때 회원가입에 성공하고 로그인 페이지로 이동한다.")
        public void givenSignUpInfoWhenRequestSignUpThenSuccessAndRedirectLoginPage() throws IOException {
            // given
            InputStream in = new FileInputStream(testDirectory + "Http_Post_users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            HttpRequest request = HttpRequestParser.parseHttpRequest(reader);
            HttpResponse response = new HttpResponse();
            FrontControllerServlet frontController = new FrontControllerServlet();
            // when
            frontController.service(request, response);
            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(response.getStatusLine().getHttpStatus()).isEqualTo(FOUND);
            assertions.assertThat(response.getResponseHeader().get(LOCATION)).isEqualTo("/login");
        }

        @Test
        @DisplayName("회원가입 정보가 주어지고 사용자가 회원가입을 요청할때 아이디 중복이 발생하여 4xx 페이지로 이동한다.")
        public void givenSignUpInfoWhenRequestSignUpThenDuplicatedIdAndForward4xxPage() throws IOException {
            // given
            User user = User.builder().userId("yonghwan1107").password("yonghwan1107").name("김용환").email("yonghwan1107@naver.com").build();
            new MemoryUserRepository().save(user);
            InputStream in = new FileInputStream(testDirectory + "Http_Post_users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            HttpRequest request = HttpRequestParser.parseHttpRequest(reader);
            HttpResponse response = new HttpResponse();
            FrontControllerServlet frontController = new FrontControllerServlet();
            // when
            frontController.service(request, response);
            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(response.getStatusLine().getHttpStatus()).isEqualTo(ALREADY_EXIST_USERID);
        }

    }

    @Nested
    @DisplayName("로그인 컨트롤러 테스트")
    class LoginControllerTest {

        @BeforeEach
        public void signup() {
            User user = User.builder().userId("yonghwan1107").password("yonghwan1107").name("김용환").email("yonghwan1107@naver.com").build();
            new MemoryUserRepository().save(user);
        }

        @AfterEach
        public void clean() {
            new MemoryUserRepository().deleteAll();
        }

        @Test
        @DisplayName("로그인 정보가 주어지고 사용자가 로그인을 요청했을때 로그인에 성공하고 질문 목록 페이지로 이동합니다.")
        public void givenLoginInfoWhenRequestLoginThenSuccessAndListQuestionPage() throws IOException {
            // given
            InputStream in = new FileInputStream(testDirectory + "Http_Post_login.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            HttpRequest request = HttpRequestParser.parseHttpRequest(reader);
            HttpResponse response = new HttpResponse();
            FrontControllerServlet frontController = new FrontControllerServlet();
            // when
            frontController.service(request, response);
            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(response.getStatusLine().getHttpStatus()).isEqualTo(FOUND);
            assertions.assertThat(response.getResponseHeader().get(LOCATION)).isEqualTo("/");
        }

        @Test
        @DisplayName("로그인 정보가 주어지고 사용자가 로그인을 요청했을 때 로그인 정보가 맞지 않아 로그인 실패 페이지로 이동한다.")
        public void givenLoginInfoWhenRequestLoginThenFailAndRedirectLoginFailedPage() throws IOException {
            // given
            User user = User.builder().userId("yonghwan1107").password("yonghwan").name("김용환").email("yonghwan1107@naver.com").build();
            new MemoryUserRepository().save(user);
            InputStream in = new FileInputStream(testDirectory + "Http_Post_login.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            HttpRequest request = HttpRequestParser.parseHttpRequest(reader);
            HttpResponse response = new HttpResponse();
            FrontControllerServlet frontController = new FrontControllerServlet();
            // when
            frontController.service(request, response);
            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(response.getStatusLine().getHttpStatus()).isEqualTo(FOUND);
            assertions.assertThat(response.getResponseHeader().get(LOCATION)).isEqualTo("/login_failed");
        }
    }

    @Nested
    @DisplayName("질문 게시글 컨트롤러 테스트")
    class QuestionControllerTest {
        @BeforeEach
        public void signup() {
            User user = User.builder().userId("yonghwan1107").password("yonghwan").name("김용환").email("yonghwan1107@naver.com").build();
            new MemoryUserRepository().save(user);
        }

        @AfterEach
        public void clean() {
            new MemoryUserRepository().deleteAll();
        }

        @Test
        @DisplayName("질문 목록 경로(/)가 주어지고 사용자가 질문 목록 페이지로 이동했을 때 질문 목록 페이지로 이동한다.")
        public void givenQuestionListPagePathWhenRequestQuestionListThenForwardQuestionListPage() throws IOException {
            // given
            InputStream in = new FileInputStream(testDirectory + "Http_Get_qna.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            HttpRequest request = HttpRequestParser.parseHttpRequest(reader);
            HttpResponse response = new HttpResponse();
            FrontControllerServlet frontController = new FrontControllerServlet();
            // when
            frontController.service(request, response);
            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(response.getStatusLine().getHttpStatus()).isEqualTo(OK);
        }

        @Test
        @DisplayName("질문 글쓰기 정보가 주어지고 사용자가 질문 글쓰기 요청 했을때 글쓰기가 성공하고 질문 목록 페이지로 이동한다.")
        public void givenQuestionWriteInfoWhenRequestAddQuestionThenSuccessAndRedirectQuestionListPage() throws IOException {
            // given
            InputStream in = new FileInputStream(testDirectory + "Http_Post_qna.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            HttpRequest request = HttpRequestParser.parseHttpRequest(reader);
            HttpResponse response = new HttpResponse();
            FrontControllerServlet frontController = new FrontControllerServlet();
            // when
            frontController.service(request, response);
            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(response.getStatusLine().getHttpStatus()).isEqualTo(FOUND);
            assertions.assertThat(LOCATION).isEqualTo("/");
        }

        @Test
        @DisplayName("비로그인 상태에서 사용자가 글쓰기 페이지 요청시 로그인 페이지로 이동한다.")
        public void givenNonLoginWhenRequestWritePageThenRedirectLoginPage() throws IOException {
            // given
            InputStream in = new FileInputStream(testDirectory + "Http_Get_qna_new.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            HttpRequest request = HttpRequestParser.parseHttpRequest(reader);
            HttpResponse response = new HttpResponse();
            FrontControllerServlet frontController = new FrontControllerServlet();
            // when
            frontController.service(request, response);
            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(response.getStatusLine().getHttpStatus()).isEqualTo(FOUND);
            assertions.assertThat(LOCATION).isEqualTo("/login");
        }

        private void login(String userId) {
            User user = new MemoryUserRepository().findByUserId(userId).orElseThrow();
            HttpSession session = SessionContainer.getSession(null);
            session.setAttribute("user", new UserResponse(user));
        }
    }

}
