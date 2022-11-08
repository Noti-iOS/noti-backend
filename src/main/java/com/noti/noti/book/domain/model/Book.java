package com.noti.noti.book.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Book {

  private Long id;
  private String title;

  @Builder
  private Book(Long id, String title) {
    this.id = id;
    this.title = title;
  }
}
