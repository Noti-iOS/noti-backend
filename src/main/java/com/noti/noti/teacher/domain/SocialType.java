package com.noti.noti.teacher.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public enum SocialType {

  KAKAO(
      "kakao",
      "https://kapi.kakao.com/v2/user/me",
      HttpMethod.GET
  ),

  APPLE(
      "apple",
      "https://appleid.apple.com/auth/keys", // 공개키 받기
      HttpMethod.GET
  );

  private final String socialName;
  private final String uerInfoUrl;
  private final HttpMethod method;


}
