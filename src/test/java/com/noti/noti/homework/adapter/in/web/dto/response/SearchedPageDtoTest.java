package com.noti.noti.homework.adapter.in.web.dto.response;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.homework.application.port.out.SearchedHomework;
import java.util.List;
import net.jqwik.api.Arbitraries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


@DisplayName("SearchedPageDtoTest 클래스")
class SearchedPageDtoTest {

  @Nested
  class SearchedPageDtoTest_생성자는 {

    @Nested
    class out의_SearchedHomework_리스트를_받으면 {

      @Test
      void 리스트의_마지막_원소의_cursorId를_nextCursorId값으로_함() {

        int size = Arbitraries.integers().between(1, 100).sample();

        List<SearchedHomework> searchedHomeworks = MonkeyUtils.MONKEY.giveMeBuilder(
            SearchedHomework.class)
            .setNotNull("cursorId")
            .setNotNull("startDate")
            .sampleList(size);

        SearchedPageDto searchedPageDto = new SearchedPageDto(searchedHomeworks);

        Assertions.assertThat(searchedPageDto.getNextCursorId())
            .isEqualTo(searchedHomeworks.get(size - 1).getCursorId());
      }
    }

  }

}