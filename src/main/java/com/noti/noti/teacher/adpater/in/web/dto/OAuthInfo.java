package com.noti.noti.teacher.adpater.in.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthInfo {

  private String socialId;
  private String nickname;
  private String thumbnailImageUrl;

  @Builder
  private OAuthInfo(String socialId, String nickname, String thumbnailImageUrl) {
    this.socialId = socialId;
    this.nickname = nickname;
    this.thumbnailImageUrl = thumbnailImageUrl;
  }
}
