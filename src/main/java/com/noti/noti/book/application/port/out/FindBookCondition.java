package com.noti.noti.book.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindBookCondition {

  private String title;
  private Long teacherId;
}
