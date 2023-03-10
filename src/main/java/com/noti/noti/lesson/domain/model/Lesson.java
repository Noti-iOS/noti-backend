package com.noti.noti.lesson.domain.model;

import com.noti.noti.teacher.domain.Teacher;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Lesson {
  private Long id;
  private Teacher teacher;
  private String lessonName;
  private Set<DayOfWeek> days;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @Builder
  public Lesson(Long id, Teacher teacher, String lessonName, Set<DayOfWeek> days,
      LocalTime startTime,
      LocalTime endTime, LocalDateTime createdAt, LocalDateTime modifiedAt) {
    this.id = id;
    this.teacher = teacher;
    this.lessonName = lessonName;
    this.days = days;
    this.startTime = startTime;
    this.endTime = endTime;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }
}
