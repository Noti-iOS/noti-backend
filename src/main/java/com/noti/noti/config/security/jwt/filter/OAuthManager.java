package com.noti.noti.config.security.jwt.filter;

import com.noti.noti.error.exception.OauthAuthenticationException;
import com.noti.noti.teacher.domain.SocialType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthManager {

  private final Map<String, OAuthUtil> authMap;

  public String getSocialId(SocialType socialType, String token) throws OauthAuthenticationException {
      return authMap.get(socialType.getUtilName()).getSocialIdBy(token);
  }
}
