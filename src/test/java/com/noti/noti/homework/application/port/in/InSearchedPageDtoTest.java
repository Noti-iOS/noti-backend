package com.noti.noti.homework.application.port.in;

import static net.jqwik.api.Arbitraries.integers;
import static net.jqwik.api.Arbitraries.strings;
import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.homework.application.port.out.SearchedHomework;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InSearchedPageDtoTest {

  @Nested
  class InSearchedPageDto_생성자는 {

    @Test
    void 검색된_숙제_목록이_없을_때_빈_문자열을_반환() {
      List<SearchedHomework> searchedHomeworks = createSearchedHomeworkList(0);

      InSearchedPageDto inSearchedPageDto = new InSearchedPageDto(searchedHomeworks, true);

      assertThat(inSearchedPageDto.getNextCursorId()).isEmpty();
    }

    @Test
    void 검색된_숙제_목록이_있다면_마지막_cursorId를_반환() {
      int size = integers().between(1, 9).sample();
      List<SearchedHomework> searchedHomeworks = createSearchedHomeworkList(size);

      InSearchedPageDto inSearchedPageDto = new InSearchedPageDto(searchedHomeworks, true);

      assertThat(inSearchedPageDto.getNextCursorId()).isEqualTo(searchedHomeworks.get(size-1).getCursorId());
    }
  }

  private List<SearchedHomework> createSearchedHomeworkList(int size) {
    List<SearchedHomework> searchedHomeworks = new ArrayList<>();
    String firstCursorId = strings().ofLength(24).sample();

    for (int i = 0; i < size; i++) {
      searchedHomeworks.add(new SearchedHomework("", "", LocalTime.now(),
          LocalTime.now(), LocalDateTime.now(), firstCursorId + i));
    }

    return searchedHomeworks;
  }

}