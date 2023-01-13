package com.noti.noti.config.security.jwt.filter;

import com.noti.noti.error.exception.OauthAuthenticationException;

public interface OAuthUtil {

  String getSocialIdBy(String token) throws OauthAuthenticationException;

}
