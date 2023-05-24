package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servlet.controller.IndexController;
import webserver.util.HttpResponseUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IndexControllerTest {
    @Test
    @DisplayName("/ 요청이 들어오면 해당 컨트롤러를 호출하고, index viewName을 반환한다.")
    void process() {
        IndexController indexController = new IndexController();

        String viewName = indexController.process(null, new HttpResponseUtils());

        assertThat("index").isEqualTo(viewName);
    }
}
