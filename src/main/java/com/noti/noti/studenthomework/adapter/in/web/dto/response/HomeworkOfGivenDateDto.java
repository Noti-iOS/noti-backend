package com.noti.noti.studenthomework.adapter.in.web.dto.response;

import com.noti.noti.studenthomework.application.port.in.InHomeworkOfGivenDate;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class HomeworkOfGivenDateDto {

  @Schema(description = "수업 id", example = "1")
  private Long lessonId;
  @Schema(description = "수업 이름", example = "중1 단어&독해")
  private String lessonName;
  @Schema(description = "수업 시작 시간", example = "15:00")
  private LocalTime startTimeOfLesson;
  @Schema(description = "수업 끝 시간", example = "17:00")
  private LocalTime endTimeOfLesson;
  @Schema(description = "수업이 가지고 있는 숙제 리스트")
  private List<HomeworkDto> homeworks = new ArrayList<>();

  public HomeworkOfGivenDateDto(Long lessonId, String lessonName,
      LocalTime startTimeOfLesson, LocalTime endTimeOfLesson,
      List<InHomeworkOfGivenDate.HomeworkDto> inHomeworks) {
    this.lessonId = lessonId;
    this.lessonName = lessonName;
    this.startTimeOfLesson = startTimeOfLesson;
    this.endTimeOfLesson = endTimeOfLesson;
    inHomeworks.forEach(
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
  private class HomeworkDto {

    @Schema(description = "숙제 id", example = "2")
    private Long homeworkId;
    @Schema(description = "숙제 내용", example = "프린트물(기출문제) 풀기")
    private String homeworkContent;
    @Schema(description = "숙제를 제공받은 학생 수", example = "7")
    private Long studentCnt;
    @Schema(description = "숙제를 완료한 학생 수", example = "6")
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
