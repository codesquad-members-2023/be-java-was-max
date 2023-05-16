package response;

import static response.ResponseBody.*;

public enum StatusCode {
	OK("200 OK \r\n", "GET"),
	FOUND("302 FOUND \r\nLocation: " + HOME_PATH + "\r\n", "POST");

	private String statusCode;
	private String httpMethod;

	StatusCode(String statusCode, String httpMethod) {
		this.statusCode = statusCode;
		this.httpMethod = httpMethod;
	}

	public static String getStatusCode(String httpMethod) {
		StatusCode[] statusCodes = values();
		for (StatusCode code : statusCodes) {
			if (code.httpMethod.equals(httpMethod)) {
				return code.statusCode;
			}
		}
		return null;
	}
}
