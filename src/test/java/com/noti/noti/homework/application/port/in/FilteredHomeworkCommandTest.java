package com.noti.noti.homework.application.port.in;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FilteredHomeworkCommandTest {

  @Nested
  class FilteredHomeworkCommand_생성자는 {

    @Test
    void 조건이_유효하고_월이_10_이상일_때_command_생성() {
      FilteredHomeworkCommand command = new FilteredHomeworkCommand(1L, 1L,2021, 10);

      assertThat(command)
          .isNotNull()
          .isInstanceOf(FilteredHomeworkCommand.class);

      assertThat(command.getLessonId()).isEqualTo(1L);
      assertThat(command.getDate()).isEqualTo(LocalDateTime.of(2021, 10, 1, 0, 0));
    }

    @Test
    void 조건이_유효하고_월이_10_미만일_때_command_생성() {
      FilteredHomeworkCommand command = new FilteredHomeworkCommand(1L, 1L,2021, 7);

      assertThat(command)
          .isNotNull()
          .isInstanceOf(FilteredHomeworkCommand.class);

      assertThat(command.getLessonId()).isEqualTo(1L);
      assertThat(command.getDate()).isEqualTo(LocalDateTime.of(2021, 7, 1, 0, 0));
    }

  }

}
