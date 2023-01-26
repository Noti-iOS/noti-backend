package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;

public class CustomIllegalArgumentException extends BusinessException{

  public CustomIllegalArgumentException(String message) {
    super(message, ErrorCode.ILLEGAL_ARGUMENT_JWT);
  }
}
