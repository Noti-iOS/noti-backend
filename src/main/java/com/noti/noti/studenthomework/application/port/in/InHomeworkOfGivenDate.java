package com.noti.noti.studenthomework.application.port.in;

import com.noti.noti.studenthomework.application.port.out.OutHomeworkOfGivenDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class InHomeworkOfGivenDate {
  private Long lessonId;
  private String lessonName;
  private LocalTime startTimeOfLesson;
  private LocalTime endTimeOfLesson;
  private List<HomeworkDto> homeworks = new ArrayList<>();


  public InHomeworkOfGivenDate (Long lessonId, String lessonName,
      LocalTime startTimeOfLesson, LocalTime endTimeOfLesson,
      List<OutHomeworkOfGivenDate.HomeworkDto> outHomeworks) {

    this.lessonId = lessonId;
    this.lessonName = lessonName;
    this.startTimeOfLesson = startTimeOfLesson;
    this.endTimeOfLesson = endTimeOfLesson;
    outHomeworks.forEach(
        homeworkDto -> homeworks.add(
            new HomeworkDto(
                homeworkDto.getHomeworkId(),
                homeworkDto.getHomeworkContent(),
                homeworkDto.getStudentCnt(),
                homeworkDto.getCompleteCnt())
        )
    );
  }


  @Getter
  public static class HomeworkDto {

    private Long homeworkId;
    private String homeworkContent;
    private Long studentCnt;
    private Long completeCnt;

    public HomeworkDto(Long homeworkId, String homeworkContent, Long studentCnt,
        Long completeCnt) {
      this.homeworkId = homeworkId;
      this.homeworkContent = homeworkContent;
      this.studentCnt = studentCnt;
      this.completeCnt = completeCnt;
    }
  }

}
