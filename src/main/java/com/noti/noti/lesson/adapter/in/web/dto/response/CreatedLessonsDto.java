package com.noti.noti.lesson.adapter.in.web.dto.response;

import com.noti.noti.lesson.application.port.in.InCreatedLesson;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreatedLessonsDto {
  @Schema(description = "분반 명", example = "중1 단어 & 독해")
  private String lessonName;


  public CreatedLessonsDto(InCreatedLesson inCreatedLesson) {
    this.lessonName = inCreatedLesson.getLessonName();
  }
}
