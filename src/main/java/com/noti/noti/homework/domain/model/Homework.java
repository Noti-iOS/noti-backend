package com.noti.noti.homework.domain.model;

import com.noti.noti.book.domain.model.Book;
import com.noti.noti.lesson.domain.model.Lesson;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Homework {

  private Long id;
  private String content;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Lesson lesson;

  @Builder
  public Homework(Long id, String content, LocalDateTime startTime, LocalDateTime endTime,
      Lesson lesson) {
    this.id = id;
    this.content = content;
    this.startTime = startTime;
    this.endTime = endTime;
    this.lesson = lesson;
  }
}
