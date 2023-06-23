package com.noti.noti.homework.application.service;

import static net.jqwik.api.Arbitraries.integers;
import static net.jqwik.api.Arbitraries.longs;
import static net.jqwik.api.Arbitraries.strings;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.homework.application.port.in.InSearchedPageDto;
import com.noti.noti.homework.application.port.out.FindSearchedHomeworkPort;
import com.noti.noti.homework.application.port.out.SearchedHomework;
import java.util.List;
import org.assertj.core.api.Assertions;
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
@DisplayName("GetSearchedHomeworkServiceTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class GetSearchedHomeworkServiceTest {

  @InjectMocks
  GetSearchedHomeworkService getSearchedHomeworkService;

  @Mock
  FindSearchedHomeworkPort findSearchedHomeworkPort;

  @Nested
  class getInSearchedHomeworks_메소드는 {

    @Test
    void 빈_리스트를_받으면_빈_리스트를_반환한다() {
      when(findSearchedHomeworkPort.findSearchedHomeworks(anyLong(), anyString(), anyInt(), anyString()))
          .thenReturn(List.of());
      InSearchedPageDto inSearchedPageDto = getSearchedHomeworkService.getSearchedHomeworks(
          longs().greaterOrEqual(0L).sample(), strings().sample(), strings().sample(), integers().between(0, 0).sample());

      Assertions.assertThat(inSearchedPageDto.getSearchedHomeworks()).isEmpty();
    }

    @Test
    void SearchedHomework_리스트를_받으면_해당_빈리스트를_반환한다() {
      when(findSearchedHomeworkPort.findSearchedHomeworks(anyLong(), anyString(), anyInt(), anyString()))
          .thenReturn(MonkeyUtils.MONKEY.giveMeBuilder(SearchedHomework.class).setNotNull("startDate").sampleList(3));

      InSearchedPageDto inSearchedPageDto = getSearchedHomeworkService.getSearchedHomeworks(
          longs().greaterOrEqual(0L).sample(), strings().sample(), strings().sample(), integers().between(0, 0).sample());

      Assertions.assertThat(inSearchedPageDto.getSearchedHomeworks()).isNotEmpty();
    }

    @Nested
    class 반환하는_InSearchedPageDto의_last_값이 {

      int requestSize = integers().between(1, 100).sample();
      @Test
      void 요청받은_size보다_받은_size의_값이_같으면_last_값이_false() {

        int returnSize = requestSize;
        when(findSearchedHomeworkPort.findSearchedHomeworks(anyLong(), anyString(), anyInt(), anyString()))
            .thenReturn(MonkeyUtils.MONKEY.giveMeBuilder(SearchedHomework.class).setNotNull("startDate").sampleList(returnSize));
        InSearchedPageDto inSearchedPageDto = getSearchedHomeworkService.getSearchedHomeworks(longs().greaterOrEqual(0L).sample(), strings().sample(), strings().sample(), requestSize);
        Assertions.assertThat(inSearchedPageDto.getLast()).isFalse();
      }

      @Test
      void 요청받은_size보다_받은_size의_값이_작으면_last_값이_true() {
        int returnSize = requestSize - 1;
        when(findSearchedHomeworkPort.findSearchedHomeworks(anyLong(), anyString(), anyInt(), anyString()))
            .thenReturn(MonkeyUtils.MONKEY.giveMeBuilder(SearchedHomework.class).setNotNull("startDate").sampleList(returnSize));
        InSearchedPageDto inSearchedPageDto = getSearchedHomeworkService.getSearchedHomeworks(longs().greaterOrEqual(0L).sample(), strings().sample(), strings().sample(), requestSize);
        Assertions.assertThat(inSearchedPageDto.getLast()).isTrue();
      }

    }

  }


}