package com.noti.noti.teacher.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public enum SocialType {

  KAKAO(
      "kakao",
      "kakaoOAuthUtil",
      "https://kapi.kakao.com/v2/user/me",
      HttpMethod.GET
  ),

  APPLE(
      "apple",
      "appleOAuthUtil",
      "https://appleid.apple.com/auth/keys",
      HttpMethod.GET

  );

  private final String socialName;
  private final String utilName;
  private final String userInfoUrl;
  private final HttpMethod method;
}
