package cafe.errors.errorcode;


import http.common.HttpStatus;

public interface ErrorCode {

    String getName();

    HttpStatus getHttpStatus();

    String getMessage();
}
