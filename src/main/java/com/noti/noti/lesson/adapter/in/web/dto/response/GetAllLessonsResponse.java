package com.noti.noti.lesson.adapter.in.web.dto.response;

import com.noti.noti.book.adapter.in.web.response.GetAllBooksResponse;
import com.noti.noti.book.application.port.out.BookDto;
import com.noti.noti.lesson.application.port.out.LessonDto;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "모든 수업 조회 응답")
public class GetAllLessonsResponse {
  @Schema(description = "수업 ID", example = "1")
  @NotNull
  @Min(1)
  private Long id;

  @Schema(description = "수업명", example = "수학 기초반")
  @NotBlank
  private String lessonName;

  private GetAllLessonsResponse(Long id, String lessonName) {
    this.id = id;
    this.lessonName = lessonName;
  }

  public static GetAllLessonsResponse from(LessonDto lesson) {
    return new GetAllLessonsResponse(lesson.getId(), lesson.getLessonName());
  }
}
