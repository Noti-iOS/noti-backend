package com.noti.noti.error.exception;

import com.noti.noti.error.ErrorCode;

public class OauthAuthenticationException extends BusinessException{


  public OauthAuthenticationException(String message) {
    super(message, ErrorCode.OAUTH_AUTHENTICATION_FAILED);
  }
}
