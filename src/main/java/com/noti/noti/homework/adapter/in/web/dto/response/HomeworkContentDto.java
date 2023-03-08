package com.noti.noti.homework.adapter.in.web.dto.response;

import com.noti.noti.homework.application.port.in.InHomeworkContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class HomeworkContentDto {

  @Schema(description = "숙제명", example = "단어 10개 외우기")
  private String homeworkName;
  @Schema(description = "숙제를 받은 학생 수", example = "21")
  private int studentCnt;
  @Schema(description = "숙제를 완료한 학생 수", example = "3")
  private int completeCnt;

  public HomeworkContentDto(InHomeworkContent in) {
    this.homeworkName = in.getHomeworkName();
    this.studentCnt = in.getStudentCnt();
    this.completeCnt = in.getCompleteCnt();
  }
}
