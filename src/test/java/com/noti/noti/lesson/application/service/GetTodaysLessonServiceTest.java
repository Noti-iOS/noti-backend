package com.noti.noti.lesson.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.homework.application.port.out.FindTodaysHomeworkPort;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.noti.noti.homework.application.port.out.TodaysHomework.HomeworkOfStudent;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework;
import com.noti.noti.lesson.application.port.out.FindLessonPort;
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLesson.LessonOfStudent;
import com.noti.noti.lesson.application.port.out.TodaysLessonSearchConditon;
import com.noti.noti.teacher.application.port.out.FindTeacherNicknamePort;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetTodaysLessonServiceTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class GetTodaysLessonServiceTest {

  @InjectMocks
  private GetTodaysLessonService getTodaysLessonService;

  @Mock
  private FindLessonPort findLessonPort;

  @Mock
  private FindTodaysHomeworkPort findTodaysHomeworkPort;

  @Mock
  private FindTeacherNicknamePort findTeacherNicknamePort;

  @Nested
  class getTodaysLessons_메서드는 {

    final Long TEACHER_ID = 1L;
    final String TEACHER_NICKNAME = "teacher";

    List<TodaysLesson> createTodaysLesson() {
      final LocalTime startTime = LocalTime.now();
      final LocalTime endTime = LocalTime.now();
      final String days = LocalDateTime.now().getDayOfWeek().toString();
      LessonOfStudent student1 = new LessonOfStudent(1L, "student1", "image",false);
      LessonOfStudent student2 = new LessonOfStudent(2L, "student2", "image",false);
      LessonOfStudent student3 = new LessonOfStudent(3L, "student3", "image",false);

      TodaysLesson lesson1 = new TodaysLesson(1L, "lesson1", startTime, endTime, days,
          List.of(student1, student2, student3));
      TodaysLesson lesson2 = new TodaysLesson(2L, "lesson2", startTime, endTime, days,
          List.of(student1, student2, student3));
      TodaysLesson lesson3 = new TodaysLesson(3L, "lesson3", startTime, endTime, days,
          List.of(student1, student2, student3));

      return List.of(lesson1, lesson2, lesson3);
    }

    @Nested
    class 주어진_선생님_ID에_해당하는_수업이_존재하지_않으면 {

      @Test
      void isLessonCreated_변수가_false인_TodaysLessonHomework_객체를_반환한다() {
        when(findTeacherNicknamePort.findTeacherNickname(TEACHER_ID)).thenReturn(TEACHER_NICKNAME);
        when(findLessonPort.findTodaysLessons(
            any(TodaysLessonSearchConditon.class))).thenReturn(
            Collections.emptyList());

        TodaysLessonHomework todaysLessonHomework = getTodaysLessonService.getTodaysLessons(
            TEACHER_ID);
        assertAll(
            () -> assertThat(todaysLessonHomework.isLessonCreated()).isFalse(),
            () -> assertThat(todaysLessonHomework.getTeacherNickName()).isEqualTo(TEACHER_NICKNAME),
            () -> verify(findTodaysHomeworkPort, never()).findTodaysHomeworks(any())
        );
      }
    }

    @Nested
    class 선생님_ID에_해당하는_숙제목록_중_오늘_요일에_해당하는_수업이_존재하지_않으면 {

      List<TodaysLesson> createEmptyStudentsTodaysLesson() {
        final LocalTime startTime = LocalTime.now();
        final LocalTime endTime = LocalTime.now();
        final String days = LocalDateTime.now().plusDays(1).getDayOfWeek().toString();
        return List.of(
            new TodaysLesson(1L, "lesson1", startTime, endTime, days, Collections.emptyList()),
            new TodaysLesson(2L, "lesson2", startTime, endTime, days, Collections.emptyList()),
            new TodaysLesson(3L, "lesson3", startTime, endTime, days, Collections.emptyList()));
      }

      @Test
      void isLessonCreated_변수가_true고_수업_목록이_비어있는_TodaysLessonHomework_객체를_반환한다() {
        when(findTeacherNicknamePort.findTeacherNickname(TEACHER_ID)).thenReturn(TEACHER_NICKNAME);
        when(findLessonPort.findTodaysLessons(any(TodaysLessonSearchConditon.class)))
            .thenReturn(createEmptyStudentsTodaysLesson());

        TodaysLessonHomework todaysLessonHomework = getTodaysLessonService.getTodaysLessons(
            TEACHER_ID);
        assertAll(
            () -> assertThat(todaysLessonHomework.isLessonCreated()).isTrue(),
            () -> assertThat(todaysLessonHomework.getLessons()).isEmpty(),
            () -> verify(findTodaysHomeworkPort, never()).findTodaysHomeworks(any())
        );
      }
    }

    @Nested
    class 조건에_해당하는_수업이_존재하지만_수업에_해당하는_숙제가_존재하지_않으면 {

      @Test
      void isLessonCreated_변수가_true고_숙제_목록이_비어있는_TodaysLessonHomework_객체를_반환한다() {
        when(findTeacherNicknamePort.findTeacherNickname(TEACHER_ID)).thenReturn(TEACHER_NICKNAME);
        when(findLessonPort.findTodaysLessons(any(TodaysLessonSearchConditon.class)))
            .thenReturn(createTodaysLesson());
        when(findTodaysHomeworkPort.findTodaysHomeworks(any(TodayHomeworkCondition.class)))
            .thenReturn(Collections.emptyList());

        TodaysLessonHomework todaysLessonHomework = getTodaysLessonService.getTodaysLessons(
            TEACHER_ID);

        assertAll(
            () -> assertThat(todaysLessonHomework.isLessonCreated()).isTrue(),
            () -> assertThat(todaysLessonHomework.getLessons()).hasSize(3),
            () -> assertThat(todaysLessonHomework.getLessons()).allSatisfy(
                (teacherId, teachersLesson) -> assertThat(teachersLesson.getHomeworks()).isEmpty()),
            () -> verify(findTodaysHomeworkPort, times(1)).findTodaysHomeworks(any())
            );
      }
    }

    @Nested
    class 모든_조건이_유효한_선생님_ID가_주어지면 {
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
      void isLessonCreated_변수가_true고_숙제_수업_학생_목록이_있는_TodaysLessonHomework_객체를_반환한다() {
        when(findTeacherNicknamePort.findTeacherNickname(TEACHER_ID)).thenReturn(TEACHER_NICKNAME);
        when(findLessonPort.findTodaysLessons(any(TodaysLessonSearchConditon.class)))
            .thenReturn(createTodaysLesson());
        when(findTodaysHomeworkPort.findTodaysHomeworks(any(TodayHomeworkCondition.class)))
            .thenReturn(createTodaysHomework());

        TodaysLessonHomework todaysLessonHomework = getTodaysLessonService.getTodaysLessons(
            TEACHER_ID);

        assertAll(
            () -> assertThat(todaysLessonHomework.isLessonCreated()).isTrue(),
            () -> assertThat(todaysLessonHomework.getLessons()).hasSize(3),
            () -> assertThat(todaysLessonHomework.getLessons().get(1L).getHomeworks()).hasSize(2),
            () -> assertThat(todaysLessonHomework.getLessons().get(2L).getHomeworks()).isEmpty(),
            () -> assertThat(todaysLessonHomework.getLessons()).allSatisfy(
                (teacherId, teachersLesson) -> assertThat(teachersLesson.getStudents()).isNotEmpty()),
            () -> verify(findTodaysHomeworkPort, times(1)).findTodaysHomeworks(any()),
            () -> assertThat(todaysLessonHomework.getLessons().get(1L).getHomeworks().get(1L)
                .getNumberOfStudents()).isEqualTo(3),
            () -> assertThat(todaysLessonHomework.getLessons().get(1L).getHomeworks().get(1L)
            .getNumberOfCompletions()).isEqualTo(2),
            () -> assertThat(todaysLessonHomework.getLessons().get(1L).getHomeworks().get(1L)
            .getNumberOfStudents()).isEqualTo(3),
            () -> assertThat(todaysLessonHomework.getLessons().get(1L).getStudents().get(1L)
            .getHomeworkProgressCount()).isEqualTo(2),
            () -> assertThat(todaysLessonHomework.getLessons().get(3L).getStudents().get(1L)
            .getHomeworkProgressCount()).isEqualTo(1)
        );
      }
    }
  }
}