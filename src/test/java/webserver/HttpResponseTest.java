// package webserver;
//
// import java.io.ByteArrayOutputStream;
// import java.io.File;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.nio.file.Files;
//
// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
//
// import webserver.httpResponse.HttpResponse;
//
// class HttpResponseTest {
// 	private final String BASE_PATH = "src/main/resources";
// 	private OutputStream out;
// 	private HttpResponse httpResponse;
//
// 	@BeforeEach
// 	void setUp() {
// 		out = new ByteArrayOutputStream();
// 	}
//
// 	@Test
// 	@DisplayName("URL로 html 입력시 text/html과 해당 html파일이 response에 포함된다.")
// 	void testCreateResponseBody_IndexPage() throws IOException {
// 		// given
// 		String view = "/index.html";
// 		String expectedContentType = "text/html";
//
// 		// when
// 		httpResponse = new HttpResponse(out, view);
//
// 		// then
// 		String actualResponse = out.toString();
// 		byte[] expectedResponse = getExpectedResponse("/templates/index.html");
// 		Assertions.assertThat(actualResponse.contains(expectedContentType));
// 		Assertions.assertThat(actualResponse.contains(expectedResponse.toString()));
// 	}
//
// 	@Test
// 	@DisplayName("URL 로 css 입력시 text/css 과 해당하는 css파일이 response에 포함된다.")
// 	void testCreateResponseBody_StaticFile() throws IOException {
// 		// given
// 		String view = "/static/css/styles.css";
// 		String expectedContentType = "text/css";
//
// 		// when
// 		httpResponse = new HttpResponse(out, view);
//
// 		// then
// 		String actualResponse = out.toString();
// 		byte[] expectedResponse = getExpectedResponse("/static/css/styles.css");
// 		Assertions.assertThat(actualResponse.contains(expectedContentType));
// 		Assertions.assertThat(actualResponse.contains(expectedResponse.toString()));
// 	}
//
// 	private byte[] getExpectedResponse(String fileName) throws IOException {
// 		return Files.readAllBytes(new File(BASE_PATH + fileName).toPath());
// 	}
// }