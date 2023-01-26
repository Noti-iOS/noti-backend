package com.noti.noti.lesson.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.noti.noti.common.WithAuthUser;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.lesson.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.lesson.application.port.in.GetFrequencyOfLessonsQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


  List<FrequencyOfLessonsDto> createLessons() {

    FrequencyOfLessonsDto dto1 = new FrequencyOfLessonsDto(LocalDateTime.now().plusDays(1), 2L);
    FrequencyOfLessonsDto dto2 = new FrequencyOfLessonsDto(LocalDateTime.now().plusDays(2), 3L);
    FrequencyOfLessonsDto dto3 = new FrequencyOfLessonsDto(LocalDateTime.now().plusDays(3), 4L);
    FrequencyOfLessonsDto dto4 = new FrequencyOfLessonsDto(LocalDateTime.now().plusDays(4), 5L);

    return List.of(dto1, dto2, dto3, dto4);
  }

  List<FrequencyOfLessonsDto> notCreateLessons() {
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
          .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4));


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
          .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

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