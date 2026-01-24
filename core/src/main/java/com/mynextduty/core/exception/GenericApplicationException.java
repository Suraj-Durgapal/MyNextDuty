package com.mynextduty.core.exception;

import lombok.Getter;
import lombok.NonNull;

public class GenericApplicationException extends RuntimeException {
  @Getter private final int statusCode;

  public GenericApplicationException(@NonNull String message, int statusCode) {
    super(message);
      this.statusCode = statusCode;
  }

  public GenericApplicationException(@NonNull String message, @NonNull Integer statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public GenericApplicationException(String message, Throwable cause, int statusCode) {
    super(message, cause);
      this.statusCode = statusCode;
  }

  public GenericApplicationException(Throwable cause, int statusCode) {
    super(cause);
      this.statusCode = statusCode;
  }

  protected GenericApplicationException(
          String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int statusCode) {
    super(message, cause, enableSuppression, writableStackTrace);
      this.statusCode = statusCode;
  }
}
