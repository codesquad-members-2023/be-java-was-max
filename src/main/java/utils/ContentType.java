package utils;

public enum ContentType {

	HTML("text/html", "html"),
	CSS("text/css", "css"),
	JAVASCRIPT("application/javascript", "js"),
	ICO("image/x-icon", "ico"),
	PNG("image/png", "png"),
	JPG("image/jpeg", "jpg"),
	WOFF("application/octet-stream", "woff"),
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
		for (ContentType contentType : values()) {
			if (contentType.getExtension().equalsIgnoreCase(extension)) {
				return contentType.getValue();
			}
		}
		return HTML.getValue();
	}

}
