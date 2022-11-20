package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.BusinessException;

public class InvalidValueException extends BusinessException {

  public InvalidValueException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public InvalidValueException(String message) {
    super(message, ErrorCode.INVALID_INPUT_VALUE);
  }
}
