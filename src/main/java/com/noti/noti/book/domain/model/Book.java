package com.noti.noti.book.domain.model;

import com.noti.noti.teacher.domain.Teacher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Book {

  private Long id;
  private String title;

  private Teacher teacher;

  @Builder
  private Book(Long id, String title, Teacher teacher) {
    this.id = id;
    this.title = title;
    this.teacher = teacher;
  }
}
