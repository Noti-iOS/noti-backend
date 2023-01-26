package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;

public class CustomExpiredJwtException extends BusinessException{

  public CustomExpiredJwtException(String message) {
    super(message, ErrorCode.EXPIRED_JWT);
  }
}
