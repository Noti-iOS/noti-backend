package com.noti.noti.auth.application.port.in;

import com.noti.noti.teacher.domain.SocialType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpCommand {

  private String socialId;
  private String nickname;
  private SocialType socialType;
  private String email;
  private String thumbnailImageUrl;

  @Builder
  private SignUpCommand(String socialId, String nickname, SocialType socialType, String email,
      String thumbnailImageUrl) {
    this.socialId = socialId;
    this.nickname = nickname;
    this.socialType = socialType;
    this.email = email;
    this.thumbnailImageUrl = thumbnailImageUrl;
  }
}
