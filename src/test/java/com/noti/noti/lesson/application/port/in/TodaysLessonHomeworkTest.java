package com.noti.noti.lesson.application.port.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.noti.noti.homework.application.port.out.TodaysHomework.HomeworkOfStudent;
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLesson.LessonOfStudent;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("TodaysLessonHomeworkTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class TodaysLessonHomeworkTest {

  final String teacherNickname = "teacher";
  final LocalDateTime now = LocalDateTime.now();

  TodaysLessonHomework createEmptyTodaysLessonHomework() {
    return new TodaysLessonHomework(teacherNickname, now);
  }

  List<TodaysLesson> createTodaysLesson() {
    final LocalTime startTime = LocalTime.now();
    final LocalTime endTime = LocalTime.now();
    final String days = "MONDAY";
    LessonOfStudent student1 = new LessonOfStudent(1L, "student1", false);
    LessonOfStudent student2 = new LessonOfStudent(2L, "student2", false);
    LessonOfStudent student3 = new LessonOfStudent(3L, "student3", false);

    TodaysLesson lesson1 = new TodaysLesson(1L, "lesson1", startTime, endTime, days,
        List.of(student1, student2, student3));
    TodaysLesson lesson2 = new TodaysLesson(2L, "lesson2", startTime, endTime, days,
        List.of(student1, student2, student3));
    TodaysLesson lesson3 = new TodaysLesson(3L, "lesson3", startTime, endTime, days,
        List.of(student1, student2, student3));

    return List.of(lesson1, lesson2, lesson3);
  }

  @Nested
  class TodaysLessonHomework_생성자는 {

    @Nested
    class 현재_시간과_선생님_닉네임이_주어지면 {

      @Test
      void TodaysLessonHomework_객체를_리턴한다() {
        TodaysLessonHomework todaysLessonHomework = new TodaysLessonHomework(teacherNickname, now);

        assertThat(todaysLessonHomework.getTeacherNickName()).isEqualTo(teacherNickname);
        assertThat(todaysLessonHomework.getLessons()).isEmpty();
      }
    }
  }

  @Nested
  class changeLessonCreatedStatus_메서드는 {


    @Nested
    class TodaysLessonHomework_객체가_주어지면 {

      @Test
      void 주어진_객체의_isLessonCreated_상태를_변경한다() {
        TodaysLessonHomework todaysLessonHomework = createEmptyTodaysLessonHomework();

        todaysLessonHomework.changeLessonCreatedStatus();

        assertThat(todaysLessonHomework.isLessonCreated()).isTrue();
      }
    }
  }

  @Nested
  class addLesson_메서드는 {

    @Nested
    class 학생목록이_비어있는_수업목록_List가_주어지면 {

      List<TodaysLesson> createEmptyStudentsTodaysLesson() {
        final LocalTime startTime = LocalTime.now();
        final LocalTime endTime = LocalTime.now();
        final String days = "MONDAY";
        return List.of(
            new TodaysLesson(1L, "lesson1", startTime, endTime, days, Collections.emptyList()),
            new TodaysLesson(2L, "lesson2", startTime, endTime, days, Collections.emptyList()),
            new TodaysLesson(3L, "lesson3", startTime, endTime, days, Collections.emptyList()));
      }

      @Test
      void TodaysLessonHomework_객체에_학생목록이_비어있는_수업을_추가한다() {
        TodaysLessonHomework givenTodaysLessonHomework = createEmptyTodaysLessonHomework();
        List<TodaysLesson> givenTodaysLesson = createEmptyStudentsTodaysLesson();

        givenTodaysLesson.forEach(givenTodaysLessonHomework::addLesson);

        assertAll(() -> {
          assertThat(givenTodaysLessonHomework.getLessons()).hasSize(3);
          assertThat(givenTodaysLessonHomework.getLessons()).allSatisfy(
              ((teacherId, teachersLesson) -> assertThat(teachersLesson.getStudents()).isEmpty()));
        });
      }
    }

    @Nested
    class 학생목록이_있는_수업목록_List가_주어지면 {

      @Test
      void TodaysLessonHomework_객체에_수업과_수업에_해당하는_학생목록을_추가한다() {
        TodaysLessonHomework givenTodaysLessonHomework = createEmptyTodaysLessonHomework();
        List<TodaysLesson> givenTodaysLesson = createTodaysLesson();

        givenTodaysLesson.forEach(givenTodaysLessonHomework::addLesson);

        assertAll(() -> {
          assertThat(givenTodaysLessonHomework.getLessons()).hasSize(3);
          assertThat(givenTodaysLessonHomework.getLessons()).allSatisfy(
              (teacherId, teachersLesson) -> assertThat(teachersLesson.getStudents()).hasSize(3));
        });
      }
    }
  }

  @Nested
  class addHomework_메서드는 {

    @Nested
    class 숙제목록과_해당_숙제의_학생목록이_주어지면 {

      TodaysLessonHomework createTodaysLessonHomework() {
        TodaysLessonHomework todaysLessonHomework = new TodaysLessonHomework(teacherNickname, now);
        List<TodaysLesson> todaysLesson = createTodaysLesson();
        todaysLesson.forEach(todaysLessonHomework::addLesson);
        return todaysLessonHomework;
      }

      List<TodaysHomework> createTodaysHomework() {
        HomeworkOfStudent homework1sStudent1 = new HomeworkOfStudent(1L, true);
        HomeworkOfStudent homework1sStudent2 = new HomeworkOfStudent(2L, false);
        HomeworkOfStudent homework1sStudent3 = new HomeworkOfStudent(3L, true);
        TodaysHomework lesson1sHomework1 = new TodaysHomework(1L, 1L, "homework1", "p.10",
            List.of(homework1sStudent1, homework1sStudent2, homework1sStudent3));

        HomeworkOfStudent homework2sStudent1 = new HomeworkOfStudent(1L, true);
        HomeworkOfStudent homework2sStudent2 = new HomeworkOfStudent(2L, false);
        HomeworkOfStudent homework2sStudent3 = new HomeworkOfStudent(3L, false);
        TodaysHomework lesson1sHomework2 = new TodaysHomework(1L, 2L, "homework2", "p.10",
            List.of(homework2sStudent1, homework2sStudent2, homework2sStudent3));

        HomeworkOfStudent homework3sStudent1 = new HomeworkOfStudent(1L, true);
        HomeworkOfStudent homework3sStudent2 = new HomeworkOfStudent(2L, false);
        HomeworkOfStudent homework3sStudent3 = new HomeworkOfStudent(3L, false);
        TodaysHomework lesson3sHomework1 = new TodaysHomework(3L, 3L, "homework3", "p.10",
            List.of(homework3sStudent1, homework3sStudent2, homework3sStudent3));

        return List.of(lesson1sHomework1, lesson1sHomework2, lesson3sHomework1);
      }

      @Test
      void TodaysLessonHomework_객체에_숙제목록을_추가한다() {
        TodaysLessonHomework givenTodaysLessonHomework = createTodaysLessonHomework();
        List<TodaysHomework> givenTodaysHomework = createTodaysHomework();

        givenTodaysHomework.forEach(givenTodaysLessonHomework::addHomework);

        assertAll(() -> {
          assertThat(givenTodaysLessonHomework.getLessons().get(1L).getHomeworks().get(1L)
              .getNumberOfStudents()).isEqualTo(3);
          assertThat(givenTodaysLessonHomework.getLessons().get(1L).getHomeworks().get(1L)
              .getNumberOfCompletions()).isEqualTo(2);
          assertThat(givenTodaysLessonHomework.getLessons().get(1L).getHomeworks().get(1L)
              .getNumberOfStudents()).isEqualTo(3);
          assertThat(givenTodaysLessonHomework.getLessons().get(1L).getStudents().get(1L)
              .getHomeworkProgressCount()).isEqualTo(2);
          assertThat(givenTodaysLessonHomework.getLessons().get(3L).getStudents().get(1L)
              .getHomeworkProgressCount()).isEqualTo(1);
        });
      }
    }
  }
}