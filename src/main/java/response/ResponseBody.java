package response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResponseBody {
	static final String HTML_PATH = "src/main/resources/templates";
	static final String STATIC_PATH = "src/main/resources/static";
	static final String HOME_PATH = "/index.html";
	private byte[] body;
	private String url;

	public ResponseBody(String url) throws IOException {
		this.url = url;
		setBody();
	}

	public void setBody() throws IOException {
		if(url.contains("html")) {
			body = Files.readAllBytes(new File(HTML_PATH + url).toPath());
		} else if(url.contains(".")){
			body = Files.readAllBytes(new File(STATIC_PATH + url).toPath());
		}
	}

	public byte[] getBody() {
		return body;
	}
}
