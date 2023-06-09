package utils;

import java.util.Arrays;

public enum ContentType {

	HTML("text/html;charset=utf-8", "html"),
	CSS("text/css;charset=utf-8", "css"),
	JAVASCRIPT("application/javascript", "js"),
	ICO("image/x-icon", "ico"),
	PNG("image/png", "png"),
	JPG("image/jpeg", "jpg"),
	WOFF("application/octet-stream", "woff"),
	WOFF2("application/octet-stream", "woff2"),
	TTF("application/octet-stream", "ttf");

	private final String value;
	private final String extension;

	ContentType(String value, String extension) {
		this.value = value;
		this.extension = extension;
	}

	public String getValue() {
		return value;
	}

	private String getExtension() {
		return extension;
	}

	public static String getByExtension(String extension) {
		return Arrays.stream(values())
			.filter(contentType -> contentType.getExtension().equalsIgnoreCase(extension))
			.findFirst()
			.map(ContentType::getValue)
			.orElse(HTML.getValue());
	}

}
