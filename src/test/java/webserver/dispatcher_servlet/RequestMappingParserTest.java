package webserver.dispatcher_servlet;

import java.io.IOException;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestMappingParserTest {

    @Test
    @DisplayName("특정 패키지의 RequestMapping 애노테이션을 가지고 있는 컨트롤러 클래스를 탐색한다")
    public void scanRequestMapping() throws IOException {
        // given
        String packageName = "cafe.app.user.controller";
        // when
        Map<String, Servlet> servletMap = RequestMappingParser.scanRequestMapping(packageName);
        // then
        Assertions.assertThat(servletMap.get("/user/create")).isNotNull();
    }
}
