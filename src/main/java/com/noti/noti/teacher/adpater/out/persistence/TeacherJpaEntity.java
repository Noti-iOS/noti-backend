package com.noti.noti.teacher.adpater.out.persistence;

import com.noti.noti.teacher.domain.Role;
import com.noti.noti.teacher.domain.SocialType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "teacher")
public class TeacherJpaEntity {

  @Id
  private Long id;
  private Long socialId;
  private String nickname;
  private String email;
  private String profile;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Enumerated(EnumType.STRING)
  private SocialType socialType;

  @Builder
  public TeacherJpaEntity(Long id, Long socialId, String nickname, String email, String profile,
      Role role, SocialType socialType) {
    this.id = id;
    this.socialId = socialId;
    this.nickname = nickname;
    this.email = email;
    this.profile = profile;
    this.role = role;
    this.socialType = socialType;
  }
}
