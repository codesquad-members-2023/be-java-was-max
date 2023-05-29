import http.request.HttpRequest;
import http.request.component.RequestHeader;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileUtils;
import webserver.jsp.JspCompiler;
import webserver.jsp.JspServlet;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

class JspCompilerTest {

    @Test
    @DisplayName("HttpResonse가 주어지고 jsp 파일을 요청했을때 jspServlet이 실행된 결과가 HttpResponse에 담기는지 테스트")
    public void test4() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        // given
        HttpRequest request = new HttpRequest(null, null, null, null, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse();
        File jspFile = FileUtils.readFile("/twoDice.jsp").orElseThrow();
        // when
        JspServlet servlet = JspCompiler.service(jspFile);
        servlet.service(request, response);
        // then
        OutputStreamWriter dos = response.getMessageBodyWriter();
        dos.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bos.toByteArray())));
        reader.lines().forEach(System.out::println);
    }

    @Test
    @DisplayName("index.jsp 파일을 요청했을때 비로그인 상태에서 실행 결과가 나오는지 테스트")
    public void test5() throws IOException, ClassNotFoundException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // given
        HttpRequest request = new HttpRequest(null, new RequestHeader(new HashMap<>()), null, null, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse();
        File jspFile = FileUtils.readFile("/index.jsp").orElseThrow();
        // when
        JspServlet servlet = JspCompiler.service(jspFile);
        servlet.service(request, response);
        // then
        OutputStreamWriter dos = response.getMessageBodyWriter();
        dos.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bos.toByteArray())));
        reader.lines().forEach(System.out::println);
    }

}
