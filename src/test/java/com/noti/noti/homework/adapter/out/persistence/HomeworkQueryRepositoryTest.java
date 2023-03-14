package com.noti.noti.homework.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.homework.application.port.out.OutSearchedHomework.SearchedHomework;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("test")
@Import({HomeworkQueryRepository.class, QuerydslTestConfig.class})
@DisplayName("HomeworkQueryRepositoryTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Slf4j
class HomeworkQueryRepositoryTest {


  @Autowired
  HomeworkQueryRepository homeworkQueryRepository;


  @Nested
  class findSearchedHomework_메소드는 {

    Long teacherId= 1L;
    String keyword = "math";
    int size = 4;
    String cursorId = "1";

    @Sql("/data/search.sql")
    @Nested
    class 조건이_유효하다면 {

      @Nested
      class cursorId가_0일_때 {

        String cursorId = "0";

        @Test
        public void cursorId_조건문을_실행하지_않는다() {

          List<SearchedHomework> searchedHomework = homeworkQueryRepository.findSearchedHomework(
              teacherId, keyword, size, cursorId);

          assertThat(searchedHomework.size()).isEqualTo(size);

        }

      }

      @Nested
      class cursorId가_1이상일_때 {

        @Test
        public void cursorId_조건문이_실행된다() {

          List<SearchedHomework> searchedHomework = homeworkQueryRepository.findSearchedHomework(
              teacherId, keyword, size, cursorId);
          assertThat(searchedHomework.size()).isEqualTo(size);

        }

      }

      @Test
      public void 주어진_cusorId보다_숙제의_cursorId가_크다() {

        String cursorId = "202210011305100000000004";

        List<SearchedHomework> searchedHomeworks = homeworkQueryRepository.findSearchedHomework(
            teacherId, keyword, size, cursorId);

        assertThat(searchedHomeworks.size()).isEqualTo(size);
        for (SearchedHomework searchedHomework : searchedHomeworks) {
          assertThat(searchedHomework.getCursorId()).isGreaterThan(cursorId);
        }

      }

      @Test
      public void 매개변수로_보낸_size_만큼_숙제목록이_출력된다() {

        int size = 2;

        List<SearchedHomework> searchedHomeworks = homeworkQueryRepository.findSearchedHomework(
            teacherId, keyword, size, cursorId);
        assertThat(searchedHomeworks.size()).isEqualTo(size);
      }


      @Test
      public void 검색어를_포함한_숙제목록이_출력된다() {

        String keyword = "math";

        List<SearchedHomework> searchedHomeworks = homeworkQueryRepository.findSearchedHomework(
            teacherId, keyword, 11, cursorId);

        for (SearchedHomework searchedHomework : searchedHomeworks) {
          assertThat(searchedHomework.getHomeworkName()).contains(keyword);
        }

      }

      @Test
      public void 이른_날짜부터_정렬된_숙제목록_반환한다() {
        String keyword = "math";

        List<SearchedHomework> searchedHomeworks = homeworkQueryRepository.findSearchedHomework(
            teacherId, keyword, size, cursorId);

        for (int i = 0; i < size - 1; i++) {
          assertThat(searchedHomeworks.get(i).getCursorId()).isLessThan(searchedHomeworks.get(i + 1).getCursorId());
        }

      }

      @Test
      public void 날짜가_동일하면_id값이_작은_숙제목록부터_반환한다() {

        String keyword = "math1";
        int size = 2;

        List<SearchedHomework> searchedHomeworks = homeworkQueryRepository.findSearchedHomework(
            teacherId, keyword, size, "1");

        assertThat(searchedHomeworks.get(0).getStartDate()).isEqualTo(
            searchedHomeworks.get(1).getStartDate());
        assertThat(searchedHomeworks.get(0).getCursorId()).isLessThan(
            searchedHomeworks.get(1).getCursorId());

      }

      @Nested
      class 마지막_페이지_일_때 {

        @Test
        public void 남은_검색된_숙제목록만_반환한다() {

          String keyword = "math1";

          List<SearchedHomework> searchedHomeworks = homeworkQueryRepository.findSearchedHomework(
              teacherId, keyword, size, "202305011305070000000008");

          assertThat(searchedHomeworks.size()).isEqualTo(2);

        }
      }
    }
      @Sql("/data/search.sql")
      @Nested
      class 조건에_맞는_데이터가_없을_때 {

        @Test
        public void 빈_리스트를_반환한다() {

          String keyword = "abc";

          List<SearchedHomework> searchedHomeworks = homeworkQueryRepository.findSearchedHomework(
              teacherId, keyword, size, cursorId);

          assertThat(searchedHomeworks).isEmpty();
      }
    }
  }

}