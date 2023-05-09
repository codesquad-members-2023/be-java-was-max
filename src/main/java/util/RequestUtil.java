package util;

public class RequestUtil {
	public static String parseUrl(String line) {
		String tokens[] = line.split(" ");
		return tokens[1];
	}
}
