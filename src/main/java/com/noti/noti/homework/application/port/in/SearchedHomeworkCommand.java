package com.noti.noti.homework.application.port.in;

import lombok.Getter;

@Getter
public class SearchedHomeworkCommand {

  private Long teacherId;
  private String keyword;
  private String cursorId;
  private int size;

  public SearchedHomeworkCommand(Long teacherId, String keyword, String cursorId, int size) {
    this.teacherId = teacherId;
    this.keyword = keyword;
    this.cursorId = cursorId;
    this.size = size;
  }
}
