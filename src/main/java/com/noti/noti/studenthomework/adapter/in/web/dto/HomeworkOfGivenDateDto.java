package com.noti.noti.studenthomework.adapter.in.web.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HomeworkOfGivenDateDto {

  public Long lessonId;
  public String lessonName;
  public LocalTime startTimeOfLesson;
  public LocalTime endTimeOfLesson;
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

  @Data
  @NoArgsConstructor
  public static class HomeworkDto {

    public Long homeworkId;
    public String homeworkContent;
    public Long studentCnt;
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
