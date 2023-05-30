package servlet.domain.request.target;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {


    @DisplayName("Query가 여러개 있을 때 ';' 기준으로 Query를 나누고 '?' 기준으로 key,value를 구분하고 저장한다")
    @Test
    void ofSeveralParams() {
        QueryString queryString = QueryString.of("name=albert;password=testPassword;email=test@email.com");

        assertThat(queryString.contains("name", "albert")).isTrue();
        assertThat(queryString.contains("password", "testPassword")).isTrue();
        assertThat(queryString.contains("email", "test@email.com")).isTrue();
    }

    @DisplayName("Query가 하나 일 때 '?' 기준으로 key,value를 구분하고 저장한다")
    @Test
    void ofSingleParams() {
        QueryString queryString = QueryString.of("name=albert");

        assertThat(queryString.contains("name", "albert")).isTrue();
    }

    @DisplayName("parameter 개수와 같으면 true 아니면 false")
    @Test
    void isSameCount() {
        QueryString queryString = QueryString.of("name=albert;password=testPassword;email=test@email.com");

        assertThat(queryString.isSameCount(3)).isTrue();
        assertThat(queryString.isSameCount(2)).isFalse();
        assertThat(queryString.isSameCount(4)).isFalse();
    }

    @DisplayName("key,value 를 포함하면 true 아니면 false")
    @Test
    void contains() {
        QueryString queryString = QueryString.of("name=albert");

        assertThat(queryString.contains("name", "albert")).isTrue();
        assertThat(queryString.contains("name", "albert1")).isFalse();
        assertThat(queryString.contains("password", "testPassword")).isFalse();
    }
}
