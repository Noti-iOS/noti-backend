package com.noti.noti.teacher.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Teacher {

  private Long id;
  private String nickname;
  private String email;
  private String profile;

  // 권한
  private Role role;
  private String socialId;

  private SocialType socialType;

  @Builder
  public Teacher(Long id, String socialId, String nickname, String email, String profile, Role role, SocialType socialType) {
    this.id = id;
    this.socialId = socialId;
    this.nickname = nickname;
    this.email = email;
    this.profile = profile;
    this.role = role;
    this.socialType = socialType;
  }


}
