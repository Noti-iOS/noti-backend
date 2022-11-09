package com.noti.noti.lessonbook.domain.model;

import com.noti.noti.book.domain.model.Book;
import com.noti.noti.lesson.domain.model.Lesson;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LessonBook {

  private Long id;
  private Book book;
  private Lesson lesson;

  @Builder
  public LessonBook(Long id, Book book, Lesson lesson) {
    this.id = id;
    this.book = book;
    this.lesson = lesson;
  }
}
