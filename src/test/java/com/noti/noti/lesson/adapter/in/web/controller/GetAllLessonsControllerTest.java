package com.noti.noti.lesson.adapter.in.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.common.WithAuthUser;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.lesson.application.port.in.GetAllLessonsQuery;
import com.noti.noti.lesson.application.port.out.LessonDto;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import java.util.List;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GetAllLessonsController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = CustomJwtFilter.class))
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
class GetAllLessonsControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  GetAllLessonsQuery getAllLessonsQuery;

  @WithAuthUser(id = "1", role = "ROLE_TEACHER")
  @Nested
  class getAllLessons_메서드는 {

    @Nested
    class 요청에_해당하는_선생님이_존재하지_않으면 {

      @Test
      void TeacherNotFoundException_예외가_발생하고_404_응답을_반환한다() throws Exception {
        when(getAllLessonsQuery.getAllLessons(anyLong())).thenThrow(new TeacherNotFoundException());

        mockMvc.perform(get("/api/teacher/lessons"))
            .andExpectAll(
                status().isNotFound(),
                result -> assertThat(result.getResolvedException()).isInstanceOf(
                    TeacherNotFoundException.class)
            );
      }
    }

    @Nested
    class 요청에_해당하는_수업이_존재하지_않으면 {

      @Test
      void 비어있는_리스트가_담긴_200_응답을_반환한다() throws Exception {
        when(getAllLessonsQuery.getAllLessons(anyLong())).thenReturn(List.of());

        mockMvc.perform(get("/api/teacher/lessons"))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.data").isEmpty()
            );
      }
    }

    @Nested
    class 요청에_해당하는_수업이_존재하면 {

      @Test
      void GetAllLessonsResponse_리스트가_담긴_200_응답을_반환한다() throws Exception {
        List<LessonDto> givenLessonDtos = MonkeyUtils.MONKEY.giveMeBuilder(LessonDto.class)
            .set("id", 1L)
            .setNotNull("lessonName")
            .sampleList(5);

        when(getAllLessonsQuery.getAllLessons(anyLong())).thenReturn(givenLessonDtos);

        mockMvc.perform(get("/api/teacher/lessons"))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.data").isNotEmpty()
            );
      }
    }
  }

}