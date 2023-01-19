package com.noti.noti.teacher.adpater.in.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class KakaoOAuthInfo {

  private KakaoAccountDto kakaoAccount;
  private String id;
  @Getter
  public static class KakaoAccountDto {
    private String email;
    private ProfileDto profile;
  }

  @Getter
  @JsonNaming(SnakeCaseStrategy.class)
  public static class ProfileDto {
    private String thumbnailImageUrl;
    private String nickname;
  }
}
