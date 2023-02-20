package com.noti.noti.auth.application.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.BusinessException;

public class NotFoundRefreshTokenException extends BusinessException {

  public NotFoundRefreshTokenException() {
    super(ErrorCode.AUTHENTICATION_FAILED);
  }
}
