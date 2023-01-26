package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;

public class CustomMalformedJwtException extends BusinessException{

  public CustomMalformedJwtException(String message) {
    super(message, ErrorCode.MALFORMED_JWT);
  }
}
