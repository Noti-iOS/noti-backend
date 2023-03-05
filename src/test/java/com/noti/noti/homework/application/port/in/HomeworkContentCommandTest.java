package com.noti.noti.homework.application.port.in;

import static com.noti.noti.common.MonkeyUtils.MONKEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("HomeworkContentCommandTest 클래스")
class HomeworkContentCommandTest {

  @Nested
  class 모든_조건이_충족되면 {
    @Test
    void date값은_String에서_localDateTime으로_변환되어_저장된다() {
      LocalDate localDate = MONKEY.giveMeBuilder(LocalDate.class).sample();

      HomeworkContentCommand command = new HomeworkContentCommand(Mockito.anyLong(),
          localDate.toString());

      assertThat(command.getDate()).isInstanceOf(LocalDateTime.class);
    }
    @Test
    void date값은_주어진_날짜의_자정으로_변환된다() {
      LocalDate localDate = MONKEY.giveMeBuilder(LocalDate.class).sample();
      LocalDateTime localDateTime = localDate.atStartOfDay();

      HomeworkContentCommand command = new HomeworkContentCommand(Mockito.anyLong(),
          localDate.toString());

      assertThat(command.getDate()).isEqualTo(localDateTime);
    }
  }


  @Nested
  class 파라미터에_잘못된_값이_들어간다면 {
    @Test
    void date에_존재하지_않는_날짜가_들어간다면_DateTimeException() {
      assertThatThrownBy(() -> new HomeworkContentCommand(Mockito.anyLong(), "2022-02-35"))
          .isInstanceOf(DateTimeException.class);
    }
  }

}