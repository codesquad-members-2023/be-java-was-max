package webserver;

import config.WebConfig;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileUtils;
import webserver.frontcontroller.Model;
import webserver.frontcontroller.ModelAndView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

class TemplateEngineParserTest {

    private final WebConfig webConfig = WebConfig.getInstance();

    @Test
    @DisplayName("동적 HTML을 렌더링하여 byte 배열로 응답한다.")
    public void parseHtmlDynamically() {
        // given
        TemplateEngineParser templateEngineParser = TemplateEngineParser.getInstance();
        Path resource = Paths.get(webConfig.getTemplatesResourcePath() + "/index.html");
        ModelAndView modelAndView = new ModelAndView("index", new Model());
        // when
        byte[] actual = templateEngineParser.parseHtmlDynamically(resource, modelAndView);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(actual)));
        String html = br.lines().collect(Collectors.joining(System.lineSeparator()));
        // then
        Path headPath = Paths.get(webConfig.getTemplatesResourcePath() + "/include/head.html");
        String head = FileUtils.readFile(FileUtils.getFileFromPath(headPath).orElseThrow());

        Path subnavPath = Paths.get(webConfig.getTemplatesResourcePath() + "/include/subnav.html");
        String subnav = FileUtils.readFile(FileUtils.getFileFromPath(subnavPath).orElseThrow());

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(html).contains(head);
        assertions.assertThat(html).contains(subnav);
    }
}
