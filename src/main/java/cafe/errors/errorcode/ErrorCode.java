package cafe.errors.errorcode;


import webserver.common.HttpStatus;

public interface ErrorCode {

    String getName();

    HttpStatus getHttpStatus();

    String getMessage();
}
