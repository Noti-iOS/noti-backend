package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;

public class CustomSignatureException extends BusinessException{

  public CustomSignatureException(String message) {
    super(message, ErrorCode.INVALID_SIGNATURE_JWT);
  }
}
