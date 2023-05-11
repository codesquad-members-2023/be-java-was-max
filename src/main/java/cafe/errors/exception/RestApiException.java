package cafe.errors.exception;

import cafe.errors.errorcode.ErrorCode;

public class RestApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public RestApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "RestApiException{" +
            "errorCode=" + errorCode +
            '}';
    }
}
