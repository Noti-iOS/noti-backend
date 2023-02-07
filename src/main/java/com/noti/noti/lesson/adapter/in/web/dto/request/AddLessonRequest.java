package com.noti.noti.lesson.adapter.in.web.dto.request;

import com.noti.noti.lesson.application.port.in.AddLessonCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddLessonRequest {

  @NotBlank
  @Schema(description = "수업명", required = true, example = "수학")
  private String lessonName;
  @NotNull
  @Schema(description = "수업 시작 시간", required = true, example = "12:00", type = "string")
  private LocalTime startTime;
  @NotNull
  @Schema(description = "수업 끝나는 시간", required = true, example = "13:00", type = "string")
  private LocalTime endTime;
  @NotEmpty
  @Size(max = 7, min = 1)
  @Schema(description = "수업 요일", required = true, allowableValues =
      {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"},
      example = "['MONDAY', 'TUESDAY']", type = "string")
  private Set<DayOfWeek> days;
  @NotNull
  @Size
  @Schema(description = "교재 ID", required = true, example = "[1,2,3]")
  private List<Long> bookIds;
  @NotNull
  @Size
  @Schema(description = "학생 ID", required = true, example = "[1,2,3]")
  private List<Long> studentIds;

  public AddLessonCommand toCommand(Long teacherId) {
    return new AddLessonCommand(teacherId, lessonName, days, startTime, endTime, bookIds,
        studentIds);
  }

  public AddLessonRequest(String lessonName, LocalTime startTime, LocalTime endTime,
      Set<DayOfWeek> days, List<Long> bookIds, List<Long> studentIds) {
    this.lessonName = lessonName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.days = days;
    this.bookIds = bookIds;
    this.studentIds = studentIds;
  }
}
