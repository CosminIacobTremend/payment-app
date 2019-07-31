package com.payment.app.lib.exceptions;

public class InvalidTransactionException extends RuntimeException {

  public InvalidTransactionException(String message) {
    super(message);
  }

  public InvalidTransactionException(Throwable cause) {
    super(cause);
  }

}
