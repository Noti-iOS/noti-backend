package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;

public class InvalidRequestException extends BusinessException{

  public InvalidRequestException(String message) {
    super(message, ErrorCode.INVALID_REQUEST);
  }
}
