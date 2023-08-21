package com.noti.noti.lesson.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.common.adapter.out.persistance.DaySetConvertor;
import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.lesson.application.port.out.FrequencyOfLessons;
import com.noti.noti.lesson.application.port.out.LessonDto;
import com.noti.noti.lesson.application.port.out.OutCreatedLesson;
import com.noti.noti.lesson.application.port.out.StudentsInLesson;
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLessonSearchConditon;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import com.noti.noti.teacher.domain.Teacher;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
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

  @Sql("/data/lesson.sql")
  @Nested
  class findLessonAndStudentsById_메서드는 {

    @Nested
    class 조건에_해당하는_수업이_존재하지_않으면 {

      @Test
      void 비어있는_Optional_객체를_반환한다() {
        Optional<StudentsInLesson> studentsInLesson = lessonPersistenceAdapter
            .findLessonAndStudentsById(6L);

        assertThat(studentsInLesson).isNotPresent();
      }
    }

    @Nested
    class 조건에_해당하는_학생이_존재하지_않는다면 {

      @Test
      void 비어있는_Optional_객체를_반환한다() {
        Optional<StudentsInLesson> returnedStudentsInLesson = lessonPersistenceAdapter
            .findLessonAndStudentsById(4L);

        assertThat(returnedStudentsInLesson)
            .isPresent()
            .hasValueSatisfying(studentsInLesson ->
                assertThat(studentsInLesson.getStudentIds()).isEmpty());
      }
    }

    @Nested
    class 조건에_해당하는_수업과_학생이_존재하면 {

      @Test
      void 정보가_모두_담긴_Optional_객체를_반환한다() {
        Optional<StudentsInLesson> returnedStudentsInLesson = lessonPersistenceAdapter
            .findLessonAndStudentsById(1L);

        assertThat(returnedStudentsInLesson)
            .isPresent()
            .hasValueSatisfying(studentsInLesson ->
                assertThat(studentsInLesson.getStudentIds()).isNotEmpty());
      }
    }
  }

  @Nested
  class findAllLessonsByTeacherId_메서드는 {

    @Nested
    class 조건에_해당하는_수업이_존재하지_않으면 {

      @Test
      void 비어있는_리스트를_반환한다() {
        List<LessonDto> lessons = lessonPersistenceAdapter.findAllLessonsByTeacherId(1L);

        assertThat(lessons).isEmpty();
      }
    }

    @Sql("/data/lesson.sql")
    @Nested
    class 조건에_해당하는_수업이_존재하면 {

      @Test
      void 해당_LessonDto_리스트를_반환한다() {
        List<LessonDto> lessons = lessonPersistenceAdapter.findAllLessonsByTeacherId(1L);

        assertThat(lessons).isNotEmpty();
      }
    }
  }

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
      void 선생님_ID에_해당하는_수업목록_List를_반환한다() {
        List<TodaysLesson> todaysLessons = lessonPersistenceAdapter.findTodaysLessons(condition);

        assertThat(todaysLessons).isNotEmpty();
      }
    }
  }


  @Nested
  class findFrequencyOfLessons_메소드는 {

    @Nested
    class 년_월이_주어지고 {

      Long teacherId1 = 1L;
      Long teacherId2 = 2L;
      Long teacherId3 = 3L;

      @Sql("/data/homework-lesson.sql")
      @Nested
      class 주어진_월에_숙제가_있다면 {

        @Test
        void 주어진_월에서_숙제가_있는_날짜와_분반_수를_반환한다() {

          LocalDate now = LocalDate.now();

          List<FrequencyOfLessons> frequencyOfLessons1 = lessonPersistenceAdapter.findFrequencyOfLessons(
              now.getYear(), now.getMonth().getValue(), teacherId1);

          List<FrequencyOfLessons> frequencyOfLessons2 = lessonPersistenceAdapter.findFrequencyOfLessons(
              now.getYear(), now.getMonth().getValue(), teacherId2);

          List<FrequencyOfLessons> frequencyOfLessons3 = lessonPersistenceAdapter.findFrequencyOfLessons(
              now.getYear(), now.getMonth().getValue(), teacherId3);

          assertThat(frequencyOfLessons1).size().isEqualTo(2);
          assertThat(frequencyOfLessons2).size().isEqualTo(2);
          assertThat(frequencyOfLessons3).size().isEqualTo(0);

        }
      }

      @Sql("/data/no-homework-lesson.sql")
      @Nested
      class 주어진_월에_숙제가_없다면 {

        @Test
        void 비어있는_list를_반환한다() {
          LocalDate now = LocalDate.now();

          List<FrequencyOfLessons> frequencyOfLessons = lessonPersistenceAdapter.findFrequencyOfLessons(
              now.getYear(), now.getMonth().getValue(), teacherId1);

          assertThat(frequencyOfLessons).isEmpty();
        }

      }

    }
  }

  @Sql("/data/lesson.sql")
  @Nested
  class findCreatedLessons_메소드는 {

    @Nested
    class 선생님이_생성한_분반이_있다면 {

      Long teacherId = 1L;

      @Test
      void 생성한_분반의_리스트를_반환한다() {
        List<OutCreatedLesson> createdLessons = lessonPersistenceAdapter.findCreatedLessons(
            teacherId);

        assertThat(createdLessons).isNotEmpty();
      }
    }

    @Nested
    class 선생님이_생성한_분반이_없다면 {

      Long teacherId = 3L;

      @Test
      void 비어있는_리스트를_반환한다() {
        List<OutCreatedLesson> createdLessons = lessonPersistenceAdapter.findCreatedLessons(
            teacherId);

        assertThat(createdLessons).isEmpty();
      }
    }
  }

}