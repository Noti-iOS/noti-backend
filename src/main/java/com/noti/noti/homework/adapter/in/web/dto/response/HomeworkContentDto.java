package com.noti.noti.homework.adapter.in.web.dto.response;

import com.noti.noti.homework.application.port.in.InHomeworkContent;
import lombok.Getter;

@Getter
public class HomeworkContentDto {

  private String homeworkName;
  private int studentCnt;
  private int completeCnt;

  public HomeworkContentDto(InHomeworkContent in) {
    this.homeworkName = in.getHomeworkName();
    this.studentCnt = in.getStudentCnt();
    this.completeCnt = in.getCompleteCnt();
  }
}
