package com.noti.noti.common.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.BusinessException;

public class IllegalArgumentDateException extends BusinessException {

  public IllegalArgumentDateException() {
    super(ErrorCode.INVALID_RANGE_MONTH);
  }
}
