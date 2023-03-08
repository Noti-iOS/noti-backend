package com.noti.noti.homework.adapter.in.web.request;

import com.noti.noti.homework.application.port.in.AddHomeworksCommand;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddHomeworksRequest {

  @NotEmpty
  private List<@NotBlank String> homeworkNames;

  @NotNull
  private LocalDateTime startTime;

  @NotNull
  private LocalDateTime endTime;

  @NotNull
  @Min(1)
  private Long lessonId;

  @NotNull
  @Min(0)
  private Long deadlineAlarmSettingTime;

  public AddHomeworksCommand toCommand(Long teacherId) {
    return new AddHomeworksCommand(homeworkNames, startTime, endTime, deadlineAlarmSettingTime,
        lessonId, teacherId);
  }
}
