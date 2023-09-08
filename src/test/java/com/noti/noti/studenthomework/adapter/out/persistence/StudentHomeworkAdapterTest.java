package com.noti.noti.studenthomework.adapter.out.persistence;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.homework.domain.model.Homework;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.student.domain.model.Student;
import com.noti.noti.studenthomework.adapter.out.persistence.jpa.StudentHomeworkJpaRepository;
import com.noti.noti.studenthomework.application.port.out.OutHomeworkOfGivenDate;
import com.noti.noti.studenthomework.domain.model.StudentHomework;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
@Import({StudentHomeworkAdapter.class, StudentHomeworkQueryRepository.class,
    QuerydslTestConfig.class})
@ActiveProfiles("test")
@DisplayName("StudentHomeworkAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Slf4j
class StudentHomeworkAdapterTest {


  @Autowired
  StudentHomeworkAdapter studentHomeworkAdapter;

  @Autowired
  StudentHomeworkJpaRepository studentHomeworkJpaRepository;

  @Sql("/data/student-homework.sql")
  @Nested
  class saveAllStudentHomeworks_메서드는 {

    @Nested
    class 유효한_StudentHomework_리스트가_주어지면 {

      @Test
      void 성공적으로_해당_정보를_저장한다() {
        List<StudentHomework> studentHomeworks = MonkeyUtils.MONKEY.giveMeBuilder(
                StudentHomework.class)
            .setNull("id")
            .set("homework",
                Homework.builder().id(1L).lesson(Lesson.builder().id(1L).build()).build())
            .set("student", Student.builder().id(1L).build())
            .sampleList(10000);

        long startTime = System.currentTimeMillis();
        studentHomeworkAdapter.saveAllStudentHomeworks(studentHomeworks);
        long endTime = System.currentTimeMillis();

        log.info(("수행시간: {}"), endTime - startTime);
      }
    }
  }

  @Nested
  @Sql("/data/student-homework.sql")
  class findHomeworksOfCalendar_메소드는 {

    @Nested
    class 년_월_일이_주어지면 {

      @Nested
      class 수업이_없는_선생님_Id가_주어지면 {

        @Test
        void 빈_리스트를_반환한다() {
          List<OutHomeworkOfGivenDate> homeworks = studentHomeworkAdapter.findHomeworksOfCalendar(1L, LocalDate.now(), 1L);
          Assertions.assertThat(homeworks).isEmpty();
        }

      }

      @Nested
      class 수업이_없는_날짜를_반환하면 {

        @Test
        void 빈_리스트를_반환한다() {
          List<OutHomeworkOfGivenDate> homeworks = studentHomeworkAdapter.findHomeworksOfCalendar(1L, LocalDate.now(), 1L);
          Assertions.assertThat(homeworks).isEmpty();
        }
      }

      @Nested
      class 모든_조건이_유효하면 {

        @Test
        void 숙제_리스트를_반환한다() {
          List<OutHomeworkOfGivenDate> homeworks = studentHomeworkAdapter.findHomeworksOfCalendar(1L, LocalDate.now(), 1L);

          Assertions.assertThat(homeworks.size()).isEqualTo(4);
        }
      }
    }
  }
}