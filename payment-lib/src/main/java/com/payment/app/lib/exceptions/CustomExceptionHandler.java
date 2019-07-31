package com.payment.app.lib.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse response = ErrorResponse.builder()
        .message("internal_server_error")
        .build();
    log.error(ExceptionUtils.getStackTrace(e));

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(response);
  }

  @ExceptionHandler(InvalidTransactionException.class)
  public final ResponseEntity<ErrorResponse> handleInvalidTransactionException(Exception e) {
    ErrorResponse response = ErrorResponse.builder()
        .message(e.getMessage())
        .build();

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(response);
  }


}
