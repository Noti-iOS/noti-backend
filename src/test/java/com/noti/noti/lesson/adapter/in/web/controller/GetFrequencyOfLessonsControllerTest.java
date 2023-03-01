package com.noti.noti.lesson.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.noti.noti.common.WithAuthUser;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@DisplayName("GetFrequencyOfLessonsControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
@WebMvcTest(controllers = GetFrequencyOfLessonsController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = CustomJwtFilter.class))
class GetFrequencyOfLessonsControllerTest {

  @Autowired
  private MockMvc mockMvc;

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

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/all").params(createInfo("2023", "12"))) // ex. /api/teacher/calendar/2023/1
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

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/all").params(createInfo("2023", "2")))
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
    void 월을_잘못_전달_시_예외코드_400발생() throws Exception {
      when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(String.class), any(Long.class)))
          .thenReturn(notCreateLessons());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/all").params(createInfo("2023", "0")))
          .andExpect(MockMvcResultMatchers.status().is(400));
      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/all").params(createInfo("2023", "13")))
          .andExpect(MockMvcResultMatchers.status().is(400));

    }

    @Test
    @WithAuthUser(id = "1", role = "TEACHER")
    void 년을_잘못_전달_시_예외코드_400발생() throws Exception {
      when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(String.class), any(Long.class)))
          .thenReturn(notCreateLessons());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/all").params(createInfo("-1", "2")))
          .andExpect(MockMvcResultMatchers.status().is(400));

    }

  }

  @Nested
  class 선생님_id가_제대로_전달되지_않았을_때 {

    @Test
    void 예외코드_401발생() throws Exception {
      when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(String.class), any(Long.class)))
          .thenReturn(createLessons());
      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/all").params(createInfo("2023", "2")))
          .andExpect(MockMvcResultMatchers.status().is(401));

    }

  }

  private MultiValueMap<String, String> createInfo(String year, String month) {
    MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
    info.add("year", String.valueOf(year));
    info.add("month", String.valueOf(month));
    return info;
  }

}