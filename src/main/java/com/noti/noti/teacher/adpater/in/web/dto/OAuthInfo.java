package com.noti.noti.teacher.adpater.in.web.dto;

import com.noti.noti.teacher.domain.SocialType;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthInfo {

  private String socialId;
  private String nickname;
  private SocialType socialType;
  private String email;
  private String thumbnailImageUrl;

  public void changeNickname(String nickname) {
    if (!Objects.isNull(nickname)) {
      this.nickname = nickname;
    }
  }

  @Builder
  private OAuthInfo(String socialId, String nickname, String thumbnailImageUrl, String email,
      SocialType socialType) {
    this.socialId = socialId;
    this.nickname = nickname;
    this.socialType = socialType;
    this.email = email;
    this.thumbnailImageUrl = thumbnailImageUrl;
  }
}
