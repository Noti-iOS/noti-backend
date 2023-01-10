package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;

public class CustomAuthenticationEntryPointException extends BusinessException {

  public CustomAuthenticationEntryPointException() {
    super(ErrorCode.AUTHENTICATION_ENTRY_POINT);
  }
}
