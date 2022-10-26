package com.noti.noti.lesson.adapter.out.persistence.jpa.model;

import com.noti.noti.common.adapter.out.persistance.jpa.model.BaseTimeEntity;
import java.time.LocalTime;
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
@Table(name = "lesson")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LessonJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_id")
  private Long id;

//  @ManyToOne
//  @JoinColumn(name = "teacher_id")
//  private Teacher teacher;

  @Column
  private String lessonName;

  @Column
  private LocalTime startTime;

  @Column
  private LocalTime endTime;

  @Builder
  public LessonJpaEntity(Long id, String lessonName, LocalTime startTime, LocalTime endTime) {
    this.id = id;
    this.lessonName = lessonName;
    this.startTime = startTime;
    this.endTime = endTime;
  }
}
