package com.noti.noti.lesson.adapter.in.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddLessonResponse {

  @Schema(description = "응답 성공 여부", example = "true")
  private boolean status;

  public AddLessonResponse(boolean status) {
    this.status = status;
  }
}
