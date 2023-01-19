package com.noti.noti.config.security.jwt.filter;

import com.noti.noti.error.exception.OauthAuthenticationException;
import com.noti.noti.teacher.adpater.in.web.dto.OAuthInfo;

public interface OAuthUtil {

  OAuthInfo getOAuthInfo(String token) throws OauthAuthenticationException;

}
