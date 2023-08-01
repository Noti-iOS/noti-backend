package com.noti.noti.notification.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.BusinessException;

public class FcmTokenNotFoundException extends BusinessException {

  public FcmTokenNotFoundException() {
    super(ErrorCode.FCM_TOKEN_NOT_FOUND);
  }
}
