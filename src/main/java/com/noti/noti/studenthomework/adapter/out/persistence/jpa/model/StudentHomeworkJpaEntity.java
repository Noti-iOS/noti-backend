package com.noti.noti.studenthomework.adapter.out.persistence.jpa.model;

import com.noti.noti.common.adapter.out.persistance.jpa.model.BaseTimeEntity;
import com.noti.noti.homework.adapter.out.persistence.jpa.model.HomeworkJpaEntity;
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
@Table(name = "student_homework")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StudentHomeworkJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_homework_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "homework_id")
  private HomeworkJpaEntity homeworkJpaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private StudentJpaEntity studentJpaEntity;

  private boolean homeworkStatus;

  @Builder
  public StudentHomeworkJpaEntity(Long id, HomeworkJpaEntity homeworkJpaEntity, StudentJpaEntity studentJpaEntity,
      boolean homeworkStatus) {
    this.id = id;
    this.homeworkJpaEntity = homeworkJpaEntity;
    this.studentJpaEntity = studentJpaEntity;
    this.homeworkStatus = homeworkStatus;
  }
}
