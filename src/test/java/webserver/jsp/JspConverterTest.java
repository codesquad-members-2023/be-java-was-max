package webserver.jsp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static util.FileUtils.*;

class JspConverterTest {
    @Test
    @DisplayName("템플릿 자바 소스 코드를 기반으로 jsp 파일을 자바 파일로 변환한다")
    public void process() {
        // given
        File file = getFileFromPath("/sample.jsp").orElseThrow();
        String className = getNameWithoutExtension(file);
        className = className.substring(0, 1).toUpperCase() + className.substring(1);
        String jspContent = readFile(file);
        JspConverter jspConverter = new JspConverter(className, jspContent);
        // when
        File actualFile = jspConverter.process();
        // then
        String actual = readFile(actualFile);
        String expect = readFile(new File("src/test/java/webserver/jsp/ExpectSample.txt"));
        Assertions.assertThat(actual).isEqualTo(expect);
    }
}
