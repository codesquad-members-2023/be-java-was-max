package utils;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestUtilsTest {

	@Test
	@DisplayName("parseQueryString메서드를 통해 queryString을  map에 key와 value형태로 저장할수 있다.")
	void testParseQueryString() {
		//given
		String queryString = "name=kim&nickName=charlie";
		Map<String, String> expectedMap = Map.of("name", "kim", "nickName", "charlie");

		//when
		Map<String, String> actualMap = HttpRequestUtils.parseQueryString(queryString);

		//then
		Assertions.assertThat(expectedMap).isEqualTo(actualMap);
	}
}