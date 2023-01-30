package com.noti.noti.studenthomework.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HomeworkOfGivenDateDto {

  @Schema(description = "수업 id", example = "1")
  public Long lessonId;
  @Schema(description = "수업 이름", example = "중1 단어&독해")
  public String lessonName;
  @Schema(description = "수업 시작 시간", example = "15:00")
  public LocalTime startTimeOfLesson;
  @Schema(description = "수업 끝 시간", example = "17:00")
  public LocalTime endTimeOfLesson;
  @Schema(description = "수업이 가지고 있는 숙제 리스트")
  public List<HomeworkDto> homeworks = new ArrayList<>();

  public HomeworkOfGivenDateDto(Long lessonId, String lessonName,
      LocalTime startTimeOfLesson, LocalTime endTimeOfLesson,
      List<HomeworkDto> homeworks) {
    this.lessonId = lessonId;
    this.lessonName = lessonName;
    this.startTimeOfLesson = startTimeOfLesson;
    this.endTimeOfLesson = endTimeOfLesson;
    this.homeworks = homeworks;
  }

  @Getter
  @NoArgsConstructor
  public static class HomeworkDto {

    @Schema(description = "숙제 id", example = "2")
    public Long homeworkId;
    @Schema(description = "숙제 내용", example = "프린트물(기출문제) 풀기")
    public String homeworkContent;
    @Schema(description = "숙제를 제공받은 학생 수", example = "7")
    public Long studentCnt;
    @Schema(description = "숙제를 완료한 학생 수", example = "6")
    public Long completeCnt;

    public HomeworkDto(Long homeworkId, String homeworkContent, Long studentCnt,
        Long completeCnt) {
      this.homeworkId = homeworkId;
      this.homeworkContent = homeworkContent;
      this.studentCnt = studentCnt;
      this.completeCnt = completeCnt;
    }
  }
}
