package com.noti.noti.lesson.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.common.WithAuthUser;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.noti.noti.homework.application.port.out.TodaysHomework.HomeworkOfStudent;
import com.noti.noti.lesson.application.port.in.GetTodaysLessonQuery;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(GetTodaysLessonInfoController.class)
@DisplayName("GetTodaysLessonInfoControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class GetTodaysLessonInfoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @MockBean
  private GetTodaysLessonQuery getTodaysLessonQuery;

  private ObjectMapper objectMapper = new ObjectMapper();

  final String TEACHER_NAME = "teacher";
  final String ID = "1";

  TodaysLessonHomework createEmptyTodaysLessonHomework() {
    return new TodaysLessonHomework(TEACHER_NAME, LocalDateTime.now());
  }

  List<TodaysLesson> createTodaysLesson() {
    final LocalTime startTime = LocalTime.now();
    final LocalTime endTime = LocalTime.now();
    final String days = LocalDateTime.now().getDayOfWeek().toString();
    LessonOfStudent student1 = new LessonOfStudent(1L, "student1", false);
    LessonOfStudent student2 = new LessonOfStudent(2L, "student2", false);
    LessonOfStudent student3 = new LessonOfStudent(3L, "student3", false);

    TodaysLesson lesson1 =
        new TodaysLesson(1L, "lesson1", startTime, endTime, days,
            List.of(student1, student2, student3));
    TodaysLesson lesson2 =
        new TodaysLesson(2L, "lesson2", startTime.minusHours(2L), endTime, days,
            List.of(student1, student2, student3));
    TodaysLesson lesson3 =
        new TodaysLesson(3L, "lesson3", startTime.minusHours(4L), endTime, days,
            List.of(student1, student2, student3));

    return List.of(lesson3, lesson2, lesson1);
  }


  @Nested
  class todaysLessonInfo_메서드는 {

    @Nested
    class 해당_선생님이_수업을_생성한적이_없으면 {

      @Test
      @WithAuthUser(id = ID, role = "TEACHER")
      void isLessonCreated가_false_이고_수업목록이_비어있는_응답객체를_반환한다() throws Exception {
        when(getTodaysLessonQuery.getTodaysLessons(any(Long.class))).thenReturn(
            createEmptyTodaysLessonHomework());

        mockMvc.perform(get("/api/teacher/home"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.teacherNickName").value(TEACHER_NAME))
            .andExpect(jsonPath("$.isLessonCreated").value(false));
      }
    }

    @Nested
    class 해당_선생님의_오늘에_해당하는_수업이_없으면 {

      @Test
      @WithAuthUser(id = ID, role = "TEACHER")
      void isLessonCreated가_true_이고_수업목록이_비어있는_응답객체를_반환한다() throws Exception {
        TodaysLessonHomework givenTodaysLessonHomework = createEmptyTodaysLessonHomework();
        givenTodaysLessonHomework.changeLessonCreatedStatus();

        when(getTodaysLessonQuery.getTodaysLessons(any(Long.class))).thenReturn(
            givenTodaysLessonHomework);

        mockMvc.perform(get("/api/teacher/home"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.teacherNickName").value(TEACHER_NAME))
            .andExpect(jsonPath("$.isLessonCreated").value(true))
            .andExpect(jsonPath("$.lessons").isEmpty());
      }
    }

    @Nested
    class 오늘에_해당하는_수업이_존재하고_학생은_존재하지_않으면 {

      TodaysLessonHomework createEmptyStudentsTodaysLessonHomework() {
        final LocalTime startTime = LocalTime.now();
        final LocalTime endTime = LocalTime.now();
        final String days = LocalDateTime.now().getDayOfWeek().toString();

        TodaysLessonHomework givenTodaysLessonHomework = createEmptyTodaysLessonHomework();
        givenTodaysLessonHomework.changeLessonCreatedStatus();

        givenTodaysLessonHomework.addLesson(
            new TodaysLesson(1L, "lesson1", startTime, endTime, days, Collections.emptyList())
        );
        givenTodaysLessonHomework.addLesson(
            new TodaysLesson(2L, "lesson2", startTime, endTime, days, Collections.emptyList())
        );
        givenTodaysLessonHomework.addLesson(
            new TodaysLesson(3L, "lesson3", startTime, endTime, days, Collections.emptyList())
        );

        return givenTodaysLessonHomework;
      }

      @Test
      @WithAuthUser(id = ID, role = "TEACHER")
      void 학생목록과_숙제목록이_비어있는_응답객체를_반환한다() throws Exception {
        when(getTodaysLessonQuery.getTodaysLessons(any(Long.class))).thenReturn(
            createEmptyStudentsTodaysLessonHomework());

        mockMvc.perform(get("/api/teacher/home"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.teacherNickName").value(TEACHER_NAME))
            .andExpect(jsonPath("$.isLessonCreated").value(true))
            .andExpect(jsonPath("$.lessons.length()").value(3))
            .andDo(print());
      }
    }

    @Nested
    class 오늘에_해당하는_수업이_존재하고_숙제는_존재하지_않으면 {

      TodaysLessonHomework createEmptyHomeworksTodaysLessonHomework() {
        final LocalTime startTime = LocalTime.now();
        final LocalTime endTime = LocalTime.now();
        final String days = LocalDateTime.now().getDayOfWeek().toString();

        TodaysLessonHomework givenTodaysLessonHomework = createEmptyTodaysLessonHomework();
        givenTodaysLessonHomework.changeLessonCreatedStatus();

        LessonOfStudent student1 =
            new LessonOfStudent(1L, "student1", false);
        LessonOfStudent student2 =
            new LessonOfStudent(2L, "student2", false);
        LessonOfStudent student3 =
            new LessonOfStudent(3L, "student3", false);

        givenTodaysLessonHomework.addLesson(
            new TodaysLesson(1L, "lesson1", startTime, endTime, days,
                List.of(student1, student2, student3))
        );
        givenTodaysLessonHomework.addLesson(
            new TodaysLesson(2L, "lesson2", startTime, endTime, days,
                List.of(student1, student2, student3))
        );
        givenTodaysLessonHomework.addLesson(
            new TodaysLesson(3L, "lesson3", startTime, endTime, days,
                List.of(student1, student2, student3))
        );

        return givenTodaysLessonHomework;
      }

      @Test
      @WithAuthUser(id = ID, role = "TEACHER")
      void 숙제목록이_비어있는_응답객체를_반환한다() throws Exception {
        when(getTodaysLessonQuery.getTodaysLessons(any(Long.class))).thenReturn(
            createEmptyHomeworksTodaysLessonHomework());

        mockMvc.perform(get("/api/teacher/home"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.teacherNickName").value(TEACHER_NAME))
            .andExpect(jsonPath("$.isLessonCreated").value(true))
            .andExpect(jsonPath("$.lessons.length()").value(3))
            .andExpect(jsonPath("$.lessons[0].students.length()").value(3))
            .andDo(print());
      }
    }

    @Nested
    class 오늘에_해당하는_수업_학생_숙제목록이_존재하면 {

      TodaysLessonHomework createTodaysLessonHomework() {
        TodaysLessonHomework todaysLessonHomework = createEmptyTodaysLessonHomework();
        todaysLessonHomework.changeLessonCreatedStatus();

        createTodaysLesson().forEach(todaysLessonHomework::addLesson);
        createTodaysHomework().forEach(todaysLessonHomework::addHomework);

        return todaysLessonHomework;
      }

      List<TodaysHomework> createTodaysHomework() {
        HomeworkOfStudent homework1sStudent1 = new HomeworkOfStudent(1L, true);
        HomeworkOfStudent homework1sStudent2 = new HomeworkOfStudent(2L, false);
        HomeworkOfStudent homework1sStudent3 = new HomeworkOfStudent(3L, true);
        TodaysHomework lesson1sHomework1 =
            new TodaysHomework(1L, 1L, "homework1", "p.10",
                List.of(homework1sStudent1, homework1sStudent2, homework1sStudent3));

        HomeworkOfStudent homework2sStudent1 = new HomeworkOfStudent(1L, true);
        HomeworkOfStudent homework2sStudent2 = new HomeworkOfStudent(2L, false);
        HomeworkOfStudent homework2sStudent3 = new HomeworkOfStudent(3L, false);
        TodaysHomework lesson1sHomework2 =
            new TodaysHomework(1L, 2L, "homework2", "p.10",
                List.of(homework2sStudent1, homework2sStudent2, homework2sStudent3));

        HomeworkOfStudent homework3sStudent1 = new HomeworkOfStudent(1L, true);
        HomeworkOfStudent homework3sStudent2 = new HomeworkOfStudent(2L, false);
        HomeworkOfStudent homework3sStudent3 = new HomeworkOfStudent(3L, false);
        TodaysHomework lesson3sHomework1 =
            new TodaysHomework(3L, 3L, "homework3", "p.10",
                List.of(homework3sStudent1, homework3sStudent2, homework3sStudent3));

        return List.of(lesson1sHomework1, lesson1sHomework2, lesson3sHomework1);
      }

      @Test
      @WithAuthUser(id = ID, role = "TEACHER")
      void 오늘에_해당하는_수업_학생_숙제목록의_응답객체를_반환한다() throws Exception {
        when(getTodaysLessonQuery.getTodaysLessons(any(Long.class))).thenReturn(
            createTodaysLessonHomework());

        mockMvc.perform(get("/api/teacher/home"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.teacherNickName").value(TEACHER_NAME))
            .andExpect(jsonPath("$.isLessonCreated").value(true))
            .andExpect(jsonPath("$.lessons.length()").value(3))
            .andExpect(jsonPath("$.lessons[0].homeworkCompletionRate").value(33))
            .andDo(print());
      }
    }
  }
}