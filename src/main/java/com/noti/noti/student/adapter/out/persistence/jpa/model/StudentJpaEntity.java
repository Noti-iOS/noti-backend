package com.noti.noti.student.adapter.out.persistence.jpa.model;

import com.noti.noti.common.adapter.out.persistance.jpa.model.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id")
  private Long id;

  @Column(nullable = false)
  private Long socialId;

  @Column
  private String nickname;

  @Column
  private String email;

  @Column
  private String profileImage;

  @Builder
  StudentJpaEntity(Long id, Long socialId, String nickname, String email,
      String profileImage) {
    this.id = id;
    this.socialId = socialId;
    this.nickname = nickname;
    this.email = email;
    this.profileImage = profileImage;
  }
}
