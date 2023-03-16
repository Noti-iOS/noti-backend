package com.noti.noti.homework.application.service;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.homework.application.port.in.SearchedHomeworkCommand;
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
import org.mockito.Mockito;
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

      Mockito.when(findSearchedHomeworkPort.findSearchedHomeworks(Mockito.anyLong(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
          .thenReturn(List.of());

      List<SearchedHomework> searchedHomeworks = getSearchedHomeworkService.getSearchedHomeworks(
          new SearchedHomeworkCommand(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()));

      Assertions.assertThat(searchedHomeworks).isEmpty();

    }

    @Test
    void SearchedHomework_리스트를_받으면_해당_리스트를_반환한다() {
      Mockito.when(findSearchedHomeworkPort.findSearchedHomeworks(Mockito.anyLong(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
          .thenReturn(MonkeyUtils.MONKEY.giveMeBuilder(SearchedHomework.class).sampleList(3));

      List<SearchedHomework> searchedHomeworks = getSearchedHomeworkService.getSearchedHomeworks(
          new SearchedHomeworkCommand(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()));

      Assertions.assertThat(searchedHomeworks).isNotEmpty();
    }

  }


}