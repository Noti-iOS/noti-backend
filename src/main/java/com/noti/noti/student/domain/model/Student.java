package com.noti.noti.student.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Student {

  private Long id;
  private Long socialId;
  private String nickname;
  private String email;
  private String profileImage;

  @Builder
  public Student(Long id, Long socialId, String nickname, String email, String profileImage) {
    this.id = id;
    this.socialId = socialId;
    this.nickname = nickname;
    this.email = email;
    this.profileImage = profileImage;
  }
}
