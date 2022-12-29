package com.noti.noti.teacher.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public enum SocialType {

  KAKAO(
      "101010",
      "kakao",
      "https://kapi.kakao.com/v2/user/me",
      HttpMethod.GET
  ),

  APPLE(
      "010101",
      "apple",
      "https://appleid.apple.com/auth/keys", // 공개키 받기
      HttpMethod.GET
  );

  private final String socialCode;
  private final String socialName;
  private final String uerInfoUrl;
  private final HttpMethod method;


}
