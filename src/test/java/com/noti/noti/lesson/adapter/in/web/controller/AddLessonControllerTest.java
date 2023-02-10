package com.noti.noti.lesson.adapter.in.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.noti.noti.book.exception.BookNotFoundException;
import com.noti.noti.common.WithAuthUser;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.lesson.application.port.in.AddLessonCommand;
import com.noti.noti.lesson.application.port.in.AddLessonUsecase;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.student.exception.StudentNotFoundException;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AddLessonController.class)
@DisplayName("AddLessonControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import({JacksonConfiguration.class, ObjectMapper.class, JwtTokenProvider.class})
class AddLessonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AddLessonUsecase addLessonUsecase;

  final String ID = "1";

  @Nested
  class addLesson_메서드는 {

    @Nested
    class 유효한_수업추가_요청이_주어지면 {

      String createRequest() {

        return "{"
            + "\"lessonName\":\"수학\","
            + " \"startTime\": \"12:00\","
            + " \"endTime\": \"13:00\","
            + " \"days\": [\"MONDAY\",\"TUESDAY\"],"
            + " \"bookIds\": [],"
            + " \"studentIds\":[]"
            + "}";
      }

      @Test
      @WithAuthUser(id = ID, role = "ROLE_TEACHER")
      void 응답코드_201인_응답객체를_반환한다() throws Exception {
        when(addLessonUsecase.apply(any(AddLessonCommand.class)))
            .thenReturn(Lesson.builder().id(1L).build());

        mockMvc.perform(post("/api/teacher/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest())
                .with(csrf()))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value(SuccessResponse.SUCCESS_MESSAGE))
            .andExpect((result) -> header().string("Location",
                result.getRequest().getRequestURL().toString() + 1L));
      }
    }

    @Nested
    class 요청객체에_비어있는_값이_있으면 {

      String createRequest() {

        return "{"
            + "\"lessonName\":\"\","
            + " \"startTime\": \"12:00\","
            + " \"endTime\": \"13:00\","
            + " \"days\": [\"MONDAY\",\"TUESDAY\"],"
            + " \"bookIds\": [],"
            + " \"studentIds\":[]"
            + "}";
      }

      @Test
      @WithAuthUser(id = ID, role = "ROLE_TEACHER")
      void 응답코드_400_예외가_발생한다() throws Exception {

        mockMvc.perform(post("/api/teacher/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest())
                .with(csrf()))
            .andExpect(status().is4xxClientError())
            .andExpect(
                result ->
                    assertAll(
                        () -> assertThat(result.getResolvedException()).isInstanceOf(
                            MethodArgumentNotValidException.class)));
      }
    }

    @Nested
    class 요청에_해당하는_선생님이_존재하지_않으면 {

      String createRequest() {

        return "{"
            + "\"lessonName\":\"수학\","
            + " \"startTime\": \"12:00\","
            + " \"endTime\": \"13:00\","
            + " \"days\": [\"MONDAY\",\"TUESDAY\"],"
            + " \"bookIds\": [],"
            + " \"studentIds\":[]"
            + "}";
      }

      @Test
      @WithAuthUser(id = ID, role = "ROLE_TEACHER")
      void 응답코드_404_TeacherNotFoundException_예외가_발생한다() throws Exception {

        when(addLessonUsecase.apply(any(AddLessonCommand.class))).thenThrow(
            new TeacherNotFoundException());

        mockMvc.perform(post("/api/teacher/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest())
                .with(csrf()))
            .andExpect(status().is4xxClientError())
            .andExpect(
                result ->
                    assertAll(
                        () -> assertThat(result.getResolvedException()).isInstanceOf(
                            TeacherNotFoundException.class)));
      }
    }

    @Nested
    class 요청에_해당하는_교재가_존재하지_않으면 {

      String createRequest() {

        return "{"
            + "\"lessonName\":\"수학\","
            + " \"startTime\": \"12:00\","
            + " \"endTime\": \"13:00\","
            + " \"days\": [\"MONDAY\",\"TUESDAY\"],"
            + " \"bookIds\": [1,2,3],"
            + " \"studentIds\":[]"
            + "}";
      }

      @Test
      @WithAuthUser(id = ID, role = "ROLE_TEACHER")
      void 응답코드_404_BookNotFoundException_예외가_발생한다() throws Exception {

        when(addLessonUsecase.apply(any(AddLessonCommand.class))).thenThrow(
            new BookNotFoundException(1L));

        mockMvc.perform(post("/api/teacher/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest())
                .with(csrf()))
            .andExpect(status().is4xxClientError())
            .andExpect(
                result ->
                    assertAll(
                        () -> assertThat(result.getResolvedException()).isInstanceOf(
                            BookNotFoundException.class)));
      }
    }

    @Nested
    class 요청에_해당하는_학생이_존재하지_않으면 {

      String createRequest() {

        return "{"
            + "\"lessonName\":\"수학\","
            + " \"startTime\": \"12:00\","
            + " \"endTime\": \"13:00\","
            + " \"days\": [\"MONDAY\",\"TUESDAY\"],"
            + " \"bookIds\": [],"
            + " \"studentIds\":[1,2,3]"
            + "}";
      }

      @Test
      @WithAuthUser(id = ID, role = "ROLE_TEACHER")
      void 응답코드_404_StudentNotFoundException_예외가_발생한다() throws Exception {

        when(addLessonUsecase.apply(any(AddLessonCommand.class))).thenThrow(
            new StudentNotFoundException(1L));

        mockMvc.perform(post("/api/teacher/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest())
                .with(csrf()))
            .andExpect(status().is4xxClientError())
            .andExpect(
                result ->
                    assertAll(
                        () -> assertThat(result.getResolvedException()).isInstanceOf(
                            StudentNotFoundException.class)));
      }
    }
  }
}