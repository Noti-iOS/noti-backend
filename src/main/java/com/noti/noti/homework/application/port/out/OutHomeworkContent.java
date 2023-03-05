package com.noti.noti.homework.application.port.out;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OutHomeworkContent {

  private String homeworkName;
  private int studentCnt;
  private int completeCnt;

  public OutHomeworkContent(String homeworkName, int studentCnt, int completeCnt) {
    this.homeworkName = homeworkName;
    this.studentCnt = studentCnt;
    this.completeCnt = completeCnt;
  }
}
