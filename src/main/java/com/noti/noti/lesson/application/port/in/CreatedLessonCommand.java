package com.noti.noti.lesson.application.port.in;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatedLessonCommand {

  @NotNull
  private final Long teacherId;

  public CreatedLessonCommand(Long teacherId) {
    this.teacherId = teacherId;
  }
}
