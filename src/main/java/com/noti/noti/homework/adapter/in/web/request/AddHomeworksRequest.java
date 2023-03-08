package com.noti.noti.homework.adapter.in.web.request;

import com.noti.noti.homework.application.port.in.AddHomeworksCommand;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "숙제 이름 목록", required = true, example = "[\"수학의 정석 p.14\"]")
  private List<@NotBlank String> homeworkNames;

  @NotNull
  @Schema(description = "숙제 시작 시간", required = true, example = "2023-02-20 12:00:00", type = "string")
  private LocalDateTime startTime;

  @NotNull
  @Schema(description = "숙제 마감 시간", required = true, example = "2023-02-21 12:00:00", type = "string")
  private LocalDateTime endTime;

  @NotNull
  @Min(1)
  @Schema(description = "수업 ID", required = true, example = "1")
  private Long lessonId;

  @NotNull
  @Min(0)
  @Schema(description = "마감 알람 설정 시간", required = true, example = "1")
  private Long deadlineAlarmSettingTime;

  public AddHomeworksCommand toCommand(Long teacherId) {
    return new AddHomeworksCommand(homeworkNames, startTime, endTime, deadlineAlarmSettingTime,
        lessonId, teacherId);
  }
}
