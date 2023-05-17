package servlet.domain.request.target;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class OriginFormTest {

    @DisplayName("requestTarget에 params이 존재하지 않을 때 requestTarget를 path로 인식하고 생성한다")
    @Test
    void createIfNotContainsParams() {
        OriginForm form = OriginForm.from("/index.html");

        assertThat(form.getPathValue()).isEqualTo("/index.html");
    }

    @DisplayName("requestTarget에 params이 존재하면 requestTarget를 path로 인식하고 생성한다")
    @Test
    void createIfContainsParams() {
        OriginForm form = OriginForm.from("/index.html?name=albert");

        assertSoftly(
                softAssertions -> {
                    softAssertions.assertThat(form.getPathValue()).isEqualTo("/index.html");
                    softAssertions.assertThat(form.containsParam("name", "albert")).isTrue();
                }
        );
    }

    @DisplayName("path 값이 같으면 true 아니면 false")
    @Test
    void isSamePath() {
        OriginForm form = OriginForm.from("/index.html");

        assertSoftly(
                softAssertions -> {
                    softAssertions.assertThat(form.isSamePath("/index.html")).isTrue();
                    softAssertions.assertThat(form.isSamePath("/error.html")).isFalse();
                }
        );
    }


    @DisplayName("Parameter 개수랑 같으면 true 아니면 false")
    @Test
    void isSameParameterCount() {
        OriginForm form = OriginForm.from("/index.html?name=albert;password=testPassword");

        assertSoftly(
                softAssertions -> {
                    softAssertions.assertThat(form.isSameParameterCount(1)).isFalse();
                    softAssertions.assertThat(form.isSameParameterCount(2)).isTrue();
                    softAssertions.assertThat(form.isSameParameterCount(3)).isFalse();
                }
        );

    }

    @DisplayName("Parameter 있으면 true 아니면 false")
    @Test
    void hasParameter() {
        OriginForm form = OriginForm.from("/index.html?name=albert;password=testPassword");

        assertThat(form.hasParameter()).isTrue();

        form = OriginForm.from("/index.html");

        assertThat(form.hasParameter()).isFalse();
    }

    @DisplayName("Parameter 포함 하면 true 이나면 false")
    @Test
    void containsParam() {
        OriginForm form = OriginForm.from("/index.html?name=albert");

        assertThat(form.containsParam("name", "albert")).isTrue();
        assertThat(form.containsParam("name", "error")).isFalse();
    }
}
