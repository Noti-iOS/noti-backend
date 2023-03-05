package com.noti.noti.homework.application.service;


import static com.noti.noti.common.MonkeyUtils.MONKEY;
import static net.jqwik.api.Arbitraries.integers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.homework.application.port.in.FilteredHomeworkCommand;
import com.noti.noti.homework.application.port.in.HomeworkContentCommand;
import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.in.InHomeworkContent;
import com.noti.noti.homework.application.port.out.FindFilteredHomeworkPort;
import com.noti.noti.homework.application.port.out.FindHomeworkContentPort;
import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.OutHomeworkContent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class GetFilteredHomeworkServiceTest {

  @InjectMocks
  GetFilteredHomeworkService getFilteredHomeworkService;

  @Mock
  FindFilteredHomeworkPort findFilteredHomeworkPort;

  @Mock
  FindHomeworkContentPort findHomeworkContentPort;

  List<OutFilteredHomeworkFrequency> createOutList() {
    OutFilteredHomeworkFrequency out1 = new OutFilteredHomeworkFrequency(
        LocalDate.of(2023, 2, 25), 2);
    OutFilteredHomeworkFrequency out2 = new OutFilteredHomeworkFrequency(
        LocalDate.of(2023, 2, 26), 1);
    OutFilteredHomeworkFrequency out3 = new OutFilteredHomeworkFrequency(
        LocalDate.of(2023, 2, 27), 4);
    return List.of(out1, out2, out3);
  }

  List<OutFilteredHomeworkFrequency> notCreateOutList() {
    return List.of();
  }


  FilteredHomeworkCommand validCommand() {
    return new FilteredHomeworkCommand(1L, 1L, 2023, 2);
  }


  @Nested
  class getFilteredHomeworks_메서드는 {
    @DisplayName("모든 조건을 충족할 때 InFilteredHomeworkFrequency 타입의 리스트 반환")
    @Test
    void meetAllCond() {

      // 어댑터로 넘어가는 포트 mock
      when(findFilteredHomeworkPort.findFilteredHomeworks(any(), anyLong(), anyLong()))
          .thenReturn(createOutList());

      List<InFilteredHomeworkFrequency> in = getFilteredHomeworkService.getFilteredHomeworks(
          validCommand());
      assertThat(in).isInstanceOf(List.class);
      assertThat(in.get(1)).isInstanceOf(InFilteredHomeworkFrequency.class);
    }

    @DisplayName("선생님이 만든 수업이 없으면 InFilteredHomeworkFrequency 타입의 빈 리스트 반환")
    @Test
    void notMeetAllCond() {
      when(findFilteredHomeworkPort.findFilteredHomeworks(any(), anyLong(), anyLong()))
          .thenReturn(notCreateOutList());
      List<InFilteredHomeworkFrequency> in = getFilteredHomeworkService.getFilteredHomeworks(validCommand());
      assertThat(in)
          .isInstanceOf(List.class)
          .isEmpty();
    }

  }

  @Nested
  class getHomeworkContents_메서드는 {

    @Nested
    class 모든_조건을_충족할_때 {

      @Test
      void Out_리스트와_같은_내용의_In_리스트_반환() {
        //given
        List<OutHomeworkContent> outList = createListRandomSizeBetween(1, 10);
        when(findHomeworkContentPort.findHomeworkContents(anyLong(), any(LocalDateTime.class)))
            .thenReturn(outList);

        //when
        HomeworkContentCommand command = MONKEY.giveMeBuilder(HomeworkContentCommand.class)
            .setNotNull("lessonId")
            .setNotNull("date")
            .sample();

        List<InHomeworkContent> inList = getFilteredHomeworkService.getHomeworkContents(command);

        //then
        assertThat(inList).usingRecursiveComparison().isEqualTo(outList);

      }

      @Test
      void 비어있는_리스트_반환() {
        //given
        List<OutHomeworkContent> outList = createListRandomSizeBetween(0, 0);
        when(findHomeworkContentPort.findHomeworkContents(anyLong(), any(LocalDateTime.class)))
            .thenReturn(outList);

        //when
        HomeworkContentCommand command = MONKEY.giveMeBuilder(HomeworkContentCommand.class)
            .setNotNull("lessonId")
            .setNotNull("date")
            .sample();

        List<InHomeworkContent> inList = getFilteredHomeworkService.getHomeworkContents(command);

        //then
        assertThat(inList).isEmpty();
      }
    }
  }

  private List<OutHomeworkContent> createListRandomSizeBetween(int min, int max) {
    Integer size = integers().between(min, max).sample();
    return MONKEY.giveMeBuilder(OutHomeworkContent.class).sampleList(size);
  }

}