package com.noti.noti.book.application.port.in;

import lombok.Getter;

@Getter
public class AddBookCommand {

  private Long teacherId;
  private String title;

  public AddBookCommand(Long teacherId, String title) {
    this.teacherId = teacherId;
    this.title = title;
  }
}
