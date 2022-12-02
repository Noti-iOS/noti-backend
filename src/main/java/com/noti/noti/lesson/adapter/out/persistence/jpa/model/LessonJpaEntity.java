package com.noti.noti.lesson.adapter.out.persistence.jpa.model;

import com.noti.noti.common.adapter.out.persistance.DaySetConvertor;
import com.noti.noti.common.adapter.out.persistance.jpa.model.BaseTimeEntity;
import com.noti.noti.teacher.adpater.out.persistence.TeacherJpaEntity;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
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
@Table(name = "lesson")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LessonJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id")
  private TeacherJpaEntity teacher;

  @Column
  private String lessonName;

  @Column
  private LocalTime startTime;

  @Column
  private LocalTime endTime;

  @Column(name = "days")
  @Convert(converter = DaySetConvertor.class)
  private Set<DayOfWeek> daySet;

  @Builder
  public LessonJpaEntity(Long id, TeacherJpaEntity teacher, String lessonName, LocalTime startTime,
      LocalTime endTime, Set<DayOfWeek> daySet) {
    this.id = id;
    this.teacher = teacher;
    this.lessonName = lessonName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.daySet = daySet;
  }
}
