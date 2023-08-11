package com.noti.noti.lesson.adapter.in.web.dto.response;

import com.noti.noti.lesson.application.port.in.InCreatedLesson;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CreatedLessonsDto {

  @Schema(description = "생성한 분반 리스트")
  private List<CreatedLesson> createdLessons = new ArrayList<>();

  public CreatedLessonsDto(List<InCreatedLesson> dtoList) {
    this.createdLessons = dtoList.stream().map(CreatedLesson::new).collect(Collectors.toList());
  }

  @Getter
  private class CreatedLesson {
    @Schema(description = "분반 아이디", example = "1")
    private Long lessonId;

    @Schema(description = "분반 명", example = "중1 단어 & 독해")
    private String lessonName;

    public CreatedLesson(InCreatedLesson inCreatedLesson) {
      this.lessonId = inCreatedLesson.getLessonId();
      this.lessonName = inCreatedLesson.getLessonName();
    }

  }

}
