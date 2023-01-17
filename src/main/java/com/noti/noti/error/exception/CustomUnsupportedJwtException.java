package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;

public class CustomUnsupportedJwtException extends BusinessException{

  public CustomUnsupportedJwtException(String message) {
    super(message, ErrorCode.UNSUPPORTED_JWT);
  }
}
