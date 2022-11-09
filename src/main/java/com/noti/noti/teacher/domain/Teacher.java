package com.noti.noti.teacher.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Teacher {

  private Long username;
  private String nickname;
  private String email;
  private String profile;

  // 권한
  private Role role;
  private Long social;

  @Builder
  public Teacher(Long id, Long social, String nickname, String email, String profile, Role role) {
    this.username = getUsername();
    this.social = social;
    this.nickname = nickname;
    this.email = email;
    this.profile = profile;
    this.role = role;
  }


}
