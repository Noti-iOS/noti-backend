package com.noti.noti.studenthomework.adapter.out.persistence;

import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.studenthomework.adapter.in.web.dto.HomeworkOfGivenDateDto;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
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
@Import({StudentHomeworkAdapter.class, StudentHomeworkQueryRepository.class, QuerydslTestConfig.class})
@ActiveProfiles("test")
@DisplayName("StudentHomeworkAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class StudentHomeworkAdapterTest {


  @Autowired
  StudentHomeworkAdapter studentHomeworkAdapter;


  @Nested
  @Sql("/data/student-homework.sql")
  class findHomeworksOfCalendar_메소드는 {
    @Nested
    class 년_월_일이_주어지면 {

      @Nested
      class 수업이_없는_선생님_Id가_주어지면{

        @Test
        void 빈_리스트를_반환한다() {
          List<HomeworkOfGivenDateDto> homeworks = studentHomeworkAdapter.findHomeworksOfCalendar(LocalDate.now(), 7L);
          Assertions.assertThat(homeworks).isEmpty();
        }

      }

      @Nested
      class 수업이_없는_날짜를_반환하면{

        @Test
        void 빈_리스트를_반환한다() {
          List<HomeworkOfGivenDateDto> homeworks = studentHomeworkAdapter.findHomeworksOfCalendar(LocalDate.now().plusDays(2), 7L);
          Assertions.assertThat(homeworks).isEmpty();
        }

      }

      @Nested
      class 모든_조건이_유효하면{

        @Test
        void 숙제_리스트를_반환한다() {
          List<HomeworkOfGivenDateDto> homeworks = studentHomeworkAdapter.findHomeworksOfCalendar(LocalDate.now(), 1L);

          Assertions.assertThat(homeworks.size()).isEqualTo(4);
        }

      }
    }

  }


}