package util;

public class RequestParser {

    public static String[] createTokens(String requestLine) {
        return requestLine.split(" ");
    }
}
