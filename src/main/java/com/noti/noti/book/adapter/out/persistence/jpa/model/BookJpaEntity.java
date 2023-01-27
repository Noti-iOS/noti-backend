package com.noti.noti.book.adapter.out.persistence.jpa.model;

import com.noti.noti.common.adapter.out.persistance.jpa.model.BaseTimeEntity;
import com.noti.noti.teacher.adpater.out.persistence.TeacherJpaEntity;
import com.noti.noti.teacher.domain.Teacher;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id")
  private Long id;

  @Column
  private String title;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id")
  private TeacherJpaEntity teacherJpaEntity;

  @Builder
  public BookJpaEntity(Long id, String title, TeacherJpaEntity teacherJpaEntity) {
    this.id = id;
    this.teacherJpaEntity = teacherJpaEntity;
    this.title = title;
  }
}
