package cafe.errors.errorcode;


import webserver.http.common.HttpStatus;

public interface ErrorCode {

    String getName();

    HttpStatus getHttpStatus();

    String getMessage();
}
