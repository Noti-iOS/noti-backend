package com.noti.noti.lesson.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;

import com.noti.noti.common.WithAuthUser;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.lesson.application.port.in.CreatedLessonCommand;
import com.noti.noti.lesson.application.port.in.GetCreatedLessonsQuery;
import com.noti.noti.lesson.application.port.in.InCreatedLesson;
import com.noti.noti.lesson.application.port.out.OutCreatedLesson;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("GetCreatedLessonsControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
@WebMvcTest(controllers = GetCreatedLessonsController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = CustomJwtFilter.class))
class GetCreatedLessonsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetCreatedLessonsQuery getCreatedLessonsQuery;

  List<InCreatedLesson> createInCreatedLessons() {

    OutCreatedLesson lesson1 = new OutCreatedLesson(1L, "lesson1");
    OutCreatedLesson lesson2 = new OutCreatedLesson(2L, "lesson2");
    OutCreatedLesson lesson3 = new OutCreatedLesson(3L, "lesson3");

    InCreatedLesson inLesson1 = new InCreatedLesson(lesson1);
    InCreatedLesson inLesson2 = new InCreatedLesson(lesson2);
    InCreatedLesson inLesson3 = new InCreatedLesson(lesson3);

    return List.of(inLesson1, inLesson2, inLesson3);
  }

  List<InCreatedLesson> createEmptyInCreatedLessons() {
    return List.of();
  }

  @Nested
  class getLessonsByTeacherId_메소드는 {

    @Nested
    class 유효한_선생님Id를_전달하면_응답코드_200 {

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 선생님이_수업을_생성했다면_해당리스트_반환() throws Exception {
        Mockito.when(getCreatedLessonsQuery.createdLessons(any(CreatedLessonCommand.class)))
            .thenReturn(createInCreatedLessons());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/createdLessons"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdLessons.size()").value(3));
      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 선생님이_생성한_수업이_없다면_빈리스트_반환() throws Exception {
        Mockito.when(getCreatedLessonsQuery.createdLessons(any(CreatedLessonCommand.class)))
            .thenReturn(createEmptyInCreatedLessons());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/createdLessons"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(SuccessResponse.SUCCESS_MESSAGE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdLessons.size()").value(0));
      }

    }

    @Test
    void 유효하지_않는_선생님Id_라면_응답코드_401() throws Exception {
      Mockito.when(getCreatedLessonsQuery.createdLessons(any(CreatedLessonCommand.class)))
          .thenReturn(createEmptyInCreatedLessons());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/createdLessons"))
          .andExpect(MockMvcResultMatchers.status().is(401));
    }

  }

}