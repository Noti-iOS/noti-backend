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
  );

  private final String socialName;
  private final String uerInfoUrl;
  private final HttpMethod method;


}
