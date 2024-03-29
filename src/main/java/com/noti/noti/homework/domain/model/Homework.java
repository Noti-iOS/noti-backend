package com.noti.noti.homework.domain.model;

import com.noti.noti.lesson.domain.model.Lesson;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Homework {

  private Long id;
  private String homeworkName;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Lesson lesson;

  @Builder
  public Homework(Long id, String homeworkName, LocalDateTime startTime, LocalDateTime endTime,
      Lesson lesson) {
    this.id = id;
    this.homeworkName = homeworkName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.lesson = lesson;
  }
}
