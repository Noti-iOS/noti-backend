package com.noti.noti.lesson.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.common.adapter.out.persistance.DaySetConvertor;
import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLesson.LessonOfStudent;
import com.noti.noti.lesson.application.port.out.TodaysLessonSearchConditon;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import com.noti.noti.teacher.domain.Teacher;
import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;
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
@Import({LessonPersistenceAdapter.class, LessonMapper.class, TeacherMapper.class,
    DaySetConvertor.class, LessonQueryRepository.class, QuerydslTestConfig.class})
@DisplayName("LessonPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Slf4j
class LessonPersistenceAdapterTest {

  @Autowired
  LessonPersistenceAdapter lessonPersistenceAdapter;

  @Nested
  class save_메소드는 {

    @Nested
    class Lesson_도메인_객체가_주어지면 {

      final Teacher giventeacher =
          Teacher.builder()
              .id(1L)
              .build();
      final Lesson givenLesson =
          Lesson.builder()
              .lessonName("Lesson")
              .days(Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))
              .teacher(giventeacher)
              .build();

      @Sql("/data/teacher.sql")
      @Test
      void 주어진_객체를_저장하고_저장된_mapping된_객체를_반환한다() {
        Lesson savedLesson = lessonPersistenceAdapter.saveLesson(givenLesson);
        List<TodaysLesson> todaysLessons = lessonPersistenceAdapter.findTodaysLessons(
            new TodaysLessonSearchConditon(1L));

        assertThat(savedLesson.getLessonName()).isEqualTo(givenLesson.getLessonName());
      }
    }
  }

  @Sql("/data/lesson.sql")
  @Nested
  class findTodaysLessons_메소드는 {

    @Nested
    class 수업이_없는_선생님의_ID가_주어지면 {

      final TodaysLessonSearchConditon condition = new TodaysLessonSearchConditon(3L);

      @Test
      void 비어있는_List를_반환한다() {
        List<TodaysLesson> todaysLessons = lessonPersistenceAdapter.findTodaysLessons(condition);

        assertThat(todaysLessons).isEmpty();
      }
    }

    @Nested
    class 수업이_있는_선생님의_ID가_주어지면 {
      final TodaysLessonSearchConditon condition = new TodaysLessonSearchConditon(1L);

      @Test
      void 선생님_ID에_해당하는_수업목록_List를_반환한다(){
        List<TodaysLesson> todaysLessons = lessonPersistenceAdapter.findTodaysLessons(condition);

        assertThat(todaysLessons).isNotEmpty();
      }
    }
  }
}