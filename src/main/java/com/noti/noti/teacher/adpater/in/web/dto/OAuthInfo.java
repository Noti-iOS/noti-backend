package com.noti.noti.teacher.adpater.in.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthInfo {

  private String socialId;
  private String nickname;

  private String email;
  private String thumbnailImageUrl;

  @Builder
  private OAuthInfo(String socialId, String nickname, String thumbnailImageUrl, String email) {
    this.socialId = socialId;
    this.nickname = nickname;
    this.email = email;
    this.thumbnailImageUrl = thumbnailImageUrl;
  }
}
