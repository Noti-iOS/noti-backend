package com.noti.noti.homework.application.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.noti.noti.homework.application.port.in.FilteredHomeworkCommand;
import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.FindFilteredHomeworkPort;
import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("GetFilteredHomeworkServiceTest 클래스")
class GetFilteredHomeworkServiceTest {

  @InjectMocks
  GetFilteredHomeworkService getFilteredHomeworkService;

  @Mock
  FindFilteredHomeworkPort findFilteredHomeworkPort;


  List<OutFilteredHomeworkFrequency> createOutList() {
    OutFilteredHomeworkFrequency out1 = new OutFilteredHomeworkFrequency(

        LocalDate.of(2023, 2, 25).atStartOfDay(), 2L);
    OutFilteredHomeworkFrequency out2 = new OutFilteredHomeworkFrequency(
        LocalDate.of(2023, 2, 26).atStartOfDay(), 1L);
    OutFilteredHomeworkFrequency out3 = new OutFilteredHomeworkFrequency(
        LocalDate.of(2023, 2, 27).atStartOfDay(), 4L);
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

    @DisplayName("모든 조건을 충족할 때 OutFilteredHomeworkFrequency 와 같은 내용 반환")
    @Test
    void return_same_content_OutFilteredHomeworkFrequency() {

      // 어댑터로 넘어가는 포트 mock
      when(findFilteredHomeworkPort.findFilteredHomeworks(any(), anyLong(), anyLong()))
          .thenReturn(createOutList());

      List<InFilteredHomeworkFrequency> in = getFilteredHomeworkService.getFilteredHomeworks(
          validCommand());

      for (int i = 0; i < 3; i++) {
        assertThat(in.get(i).getDate()).isEqualTo(createOutList().get(i).getDate());
        assertThat(in.get(i).getHomeworksCnt()).isEqualTo(createOutList().get(i).getHomeworkCnt());
      }

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


}