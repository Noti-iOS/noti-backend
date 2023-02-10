package com.noti.noti.book.adapter.in.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.noti.noti.book.application.port.in.AddBookCommand;
import com.noti.noti.book.application.port.in.AddBookUsecase;
import com.noti.noti.book.domain.model.Book;
import com.noti.noti.book.exception.DuplicatedTitleBookException;
import com.noti.noti.common.WithAuthUser;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AddBookController.class)
@DisplayName("AddBookController 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import({JacksonConfiguration.class, ObjectMapper.class, JwtTokenProvider.class})
class AddBookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AddBookUsecase addBookUsecase;

  final String TEACHER_ID = "1";
  final Long BOOK_ID = 1L;
  final String ROLE = "ROLE_TEACHER";
  final String TITLE = "수학의정석";

  @Nested
  class AddBook_메서드는 {

    @Nested
    class 요청한_선생님이_존재하지_않으면 {

      @Test
      @WithAuthUser(id = TEACHER_ID, role = ROLE)
      void TeacherNotFoundException_예외가_발생한다() throws Exception {
        when(addBookUsecase.apply(any(AddBookCommand.class))).thenThrow(
            new TeacherNotFoundException());

        mockMvc.perform(post("/api/teacher/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest(TITLE))
                .with(csrf()))
            .andExpectAll(
                status().is4xxClientError(),
                result -> assertThat(result.getResolvedException()).isInstanceOf(
                    TeacherNotFoundException.class)
            );
      }
    }

    @Nested
    class 중복된_교재명의_요청이_주어지면 {

      @Test
      @WithAuthUser(id = TEACHER_ID, role = ROLE)
      void DuplicatedTitleBookException_예외가_발생한다() throws Exception {
        when(addBookUsecase.apply(any(AddBookCommand.class))).thenThrow(
            new DuplicatedTitleBookException());

        mockMvc.perform(post("/api/teacher/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest(TITLE))
                .with(csrf()))
            .andExpectAll(
                status().is4xxClientError(),
                result -> assertThat(result.getResolvedException()).isInstanceOf(
                    DuplicatedTitleBookException.class)
            );
      }
    }

    @Nested
    class 요청이_유효하면 {

      @Test
      @WithAuthUser(id = TEACHER_ID, role = ROLE)
      void 성공적으로_요청을_저장하고_응답한다() throws Exception {
        when(addBookUsecase.apply(any(AddBookCommand.class))).thenReturn(
            Book.builder().id(BOOK_ID).build());

        mockMvc.perform(post("/api/teacher/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest(TITLE))
                .with(csrf()))
            .andExpectAll(
                status().isCreated(),
                jsonPath("$.message").value(SuccessResponse.SUCCESS_MESSAGE),
                result -> header().string("Location",
                    result.getRequest().getRequestURL().toString() + BOOK_ID)
            );
      }
    }
  }

  String createRequest(String title) {
    return "{"
        + "\"title\":\"" + title + "\""
        + "}";
  }
}