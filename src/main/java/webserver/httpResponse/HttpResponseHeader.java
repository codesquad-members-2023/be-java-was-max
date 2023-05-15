package webserver.httpResponse;

import utils.ContentType;

public class HttpResponseHeader {

	private String contentType;

	private String header;

	public HttpResponseHeader(String view, int contentLength) {
		this.contentType = ContentType.getByExtension(extractFileExtensionFromView(view));
		String actualView = parseViewPath(view);
		if (view.contains("redirect")) {
			createRedirectHeader(actualView);
		} else {
			createResponse200Header(contentLength);
		}
	}

	private String parseViewPath(String view) {
		if (view.contains("redirect")) {
			return view.substring(view.indexOf(":") + 1);
		}
		return view;
	}

	private String extractFileExtensionFromView(String view) {
		return view.substring(view.lastIndexOf(".") + 1);
	}

	private void createResponse200Header(int contentLength) {
		header = "Content-Type: " + contentType + (contentType.contains("text") ? ";charset=utf-8" : "") + "\r\n" +
			"Content-Length: " + contentLength + "\r\n" +
			"\r\n";
	}

	private void createRedirectHeader(String actualView) {
		header = "Location: " + actualView + "\r\n";
	}

	@Override
	public String toString() {
		return header;
	}
}
