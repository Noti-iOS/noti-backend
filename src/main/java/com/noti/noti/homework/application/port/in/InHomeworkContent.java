package com.noti.noti.homework.application.port.in;


import com.noti.noti.homework.application.port.out.OutHomeworkContent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InHomeworkContent {


  private String homeworkName;
  private int studentCnt;
  private int completeCnt;


  public InHomeworkContent(OutHomeworkContent out) {
    this.homeworkName = out.getHomeworkName();
    this.studentCnt = out.getStudentCnt();
    this.completeCnt = out.getCompleteCnt();
  }
}
