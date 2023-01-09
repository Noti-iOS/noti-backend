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
      "https://kapi.kakao.com/v2/user/me",
      HttpMethod.GET
  ) {
    void applyCode(@Value("${code.kakao}") String code) {
      this.code=code;
    }
  },

  APPLE(
      "apple",
      "https://appleid.apple.com/auth/keys",
      HttpMethod.GET

  ) {
    void applyCode(@Value("${code.apple}") String code) {
      this.code=code;
    }
  };

  String code; // TODO:NULL?
  private final String socialName;
  private final String userInfoUrl;
  private final HttpMethod method;

  abstract void applyCode(String code);
}
