package com.noti.noti.lesson.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.noti.noti.common.WithAuthUser;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.lesson.adapter.in.web.dto.response.FrequencyOfLessonsDto;
import com.noti.noti.lesson.application.port.in.DateFrequencyOfLessons;
import com.noti.noti.lesson.application.port.in.GetFrequencyOfLessonsQuery;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@DisplayName("GetFrequencyOfLessonsControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
@WebMvcTest(GetFrequencyOfLessonsController.class)
class GetFrequencyOfLessonsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @MockBean
  private GetFrequencyOfLessonsQuery getFrequencyOfLessonsQuery;


  List<DateFrequencyOfLessons> createLessons() {

    DateFrequencyOfLessons dateFrequencyOfLessons1 = new DateFrequencyOfLessons(LocalDate.now().plusDays(1), 2L);
    DateFrequencyOfLessons dateFrequencyOfLessons2 = new DateFrequencyOfLessons(LocalDate.now().plusDays(2), 3L);
    DateFrequencyOfLessons dateFrequencyOfLessons3 = new DateFrequencyOfLessons(LocalDate.now().plusDays(3), 4L);
    DateFrequencyOfLessons dateFrequencyOfLessons4 = new DateFrequencyOfLessons(LocalDate.now().plusDays(4), 5L);

    return List.of(dateFrequencyOfLessons1, dateFrequencyOfLessons2, dateFrequencyOfLessons3, dateFrequencyOfLessons4);
  }

  List<DateFrequencyOfLessons> notCreateLessons() {
    return List.of();
  }



  @Nested
  class 날짜_형식을_제대로_전달할_때 {

    @Test
    @WithAuthUser(id = "1", role = "TEACHER")
    void 주어진_날짜에_대한_날짜내용과_분반_수_반환() throws Exception {
      when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(String.class), any(Long.class)))
          .thenReturn(createLessons());

      LocalDate now = LocalDate.now();
      String year = Integer.toString(now.getYear());
      String month = Integer.toString(now.getMonth().getValue());
      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/{year}/{month}", year, month)) // ex. /api/teacher/calendar/2023/1
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(SuccessResponse.SUCCESS_MESSAGE))
          .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(4));


    }

    @Test
    @WithAuthUser(id = "1", role = "TEACHER")
    void 빈_리스트_반환() throws Exception {
      when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(String.class), any(Long.class)))
          .thenReturn(notCreateLessons());

      LocalDate now = LocalDate.now();
      String year = Integer.toString(now.getYear());
      String month = Integer.toString(now.getMonth().getValue());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/{year}/{month}", year, month))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(SuccessResponse.SUCCESS_MESSAGE))
          .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(0));

    }

  }

  @Nested
  class 날짜_형식을_제대로_전달하지_않았을_때 {

    @Test
    @WithAuthUser(id = "1", role = "TEACHER")
    void 예외코드_400발생() throws Exception {
      when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(String.class), any(Long.class)))
          .thenReturn(notCreateLessons());

      LocalDate now = LocalDate.now();
      String year = Integer.toString(now.getYear());
      String month = now.getMonth().toString(); // 1이 아닌, JANUARY

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/{year}/{month}", year, month))
          .andExpect(MockMvcResultMatchers.status().is(400));

    }

  }

  @Nested
  class 선생님_id가_제대로_전달되지_않았을_때 {

    @Test
    void 예외코드_401발생() throws Exception {
      when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(String.class), any(Long.class)))
          .thenReturn(createLessons());

      LocalDate now = LocalDate.now();
      String year = Integer.toString(now.getYear());
      String month = Integer.toString(now.getMonth().getValue());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/{year}/{month}", year, month))
          .andExpect(MockMvcResultMatchers.status().is(401));

    }

  }

}