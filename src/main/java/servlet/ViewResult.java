package servlet;

public class ViewResult {
	private final String viewName;
	private final byte[] body;

	public ViewResult(String viewName, byte[] body) {
		this.viewName = viewName;
		this.body = body;
	}

	public String getViewName() {
		return viewName;
	}

	public byte[] getBody() {
		return body;
	}
}
