package com.noti.noti.lesson.adapter.out.persistence.jpa.model;

import com.noti.noti.common.adapter.out.persistance.DaySetConvertor;
import com.noti.noti.common.adapter.out.persistance.jpa.model.BaseTimeEntity;
import com.noti.noti.common.domain.model.Day;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

  @Column(name = "days")
  @Convert(converter = DaySetConvertor.class)
  private Set<Day> daySet;

  @Builder
  public LessonJpaEntity(Long id, String lessonName, LocalTime startTime, LocalTime endTime,
      Set<Day> daySet) {
    this.id = id;
    this.lessonName = lessonName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.daySet = daySet;
  }
}
