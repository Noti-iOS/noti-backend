package com.noti.noti.homework.adapter.in.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.common.WithAuthUser;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.homework.adapter.in.web.request.AddHomeworksRequest;
import com.noti.noti.homework.application.port.in.AddHomeworksUsecase;
import com.noti.noti.lesson.application.exception.LessonNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AddHomeworksController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = CustomJwtFilter.class))
@DisplayName("AddHomeworksControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
class AddHomeworksControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  AddHomeworksUsecase addHomeworksUsecase;

  @Autowired
  ObjectMapper objectMapper;

  @WithAuthUser(id = "1", role = "ROLE_TEACHER")
  @Nested
  class addHomeworks_메서드는 {

    @Nested
    class 유효하지_않는_Lesson_ID가_주어지면 {

      @Test
      void LessonNotFoundException_예외가_발생하고_404_응답을_반환한다() throws Exception {
        AddHomeworksRequest addHomeworksRequest = MonkeyUtils.MONKEY.giveMeOne(
            AddHomeworksRequest.class);

        doThrow(new LessonNotFoundException()).when(addHomeworksUsecase).addHomeworks(any());
        mockMvc.perform(post("/api/teacher/homeworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(addHomeworksRequest))
                .with(csrf()))
            .andExpectAll(
                status().is4xxClientError(),
                result -> assertThat(result.getResolvedException()).isInstanceOf(
                    LessonNotFoundException.class)
            );
      }
    }

    @Nested
    class 유효한_요청이_주어지면 {

      @Test
      void 성공적으로_요청을_수행하고_201_응답을_반환한다() throws Exception {
        AddHomeworksRequest addHomeworksRequest = MonkeyUtils.MONKEY.giveMeOne(
            AddHomeworksRequest.class);

        doNothing().when(addHomeworksUsecase).addHomeworks(any());
        mockMvc.perform(post("/api/teacher/homeworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(addHomeworksRequest))
                .with(csrf()))
            .andExpect(status().isCreated());
      }
    }
  }
}