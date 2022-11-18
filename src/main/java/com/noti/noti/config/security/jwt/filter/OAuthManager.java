package com.noti.noti.config.security.jwt.filter;

import com.noti.noti.teacher.domain.SocialType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthManager {

  public final Map<String, OAuthUtil> authMap;

  public String getSocialId(SocialType socialType, String token) {
    return authMap.get(socialType).getSocialIdBy(token);
  }

}
