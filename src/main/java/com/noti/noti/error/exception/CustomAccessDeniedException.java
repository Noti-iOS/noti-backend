package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;

public class CustomAccessDeniedException extends BusinessException{

  public CustomAccessDeniedException() {
    super(ErrorCode.HANDLE_ACCESS_DENIED);
  }
}
