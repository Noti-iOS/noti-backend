package com.noti.noti.studentlesson.adapter.out.persistence.jpa.model;

import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import com.noti.noti.student.adapter.out.persistence.jpa.model.StudentJpaEntity;
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
@Table(name = "student_lesson")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentLessonJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_lesson_id")
  private Long id;

  @Column
  private boolean focusStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id")
  private LessonJpaEntity lessonJpaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private StudentJpaEntity studentJpaEntity;

  @Builder
  public StudentLessonJpaEntity(Long id, boolean focusStatus, LessonJpaEntity lessonJpaEntity,
      StudentJpaEntity studentJpaEntity) {
    this.id = id;
    this.focusStatus = focusStatus;
    this.lessonJpaEntity = lessonJpaEntity;
    this.studentJpaEntity = studentJpaEntity;
  }
}
