package com.noti.noti.lessonbook.domain.model;

import com.noti.noti.book.domain.model.Book;
import com.noti.noti.lesson.domain.model.Lesson;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LessonBook {

  private Long id;
  private Long bookId;
  private Long lessonId;

  @Builder
  public LessonBook(Long id, Long bookId, Long lessonId) {
    this.id = id;
    this.bookId = bookId;
    this.lessonId = lessonId;
  }
}
