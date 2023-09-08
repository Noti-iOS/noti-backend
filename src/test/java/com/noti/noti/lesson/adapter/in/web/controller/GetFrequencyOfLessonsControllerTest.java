package com.noti.noti.lesson.adapter.in.web.controller;

import static com.noti.noti.common.adapter.in.web.response.SuccessResponse.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.noti.noti.common.WithAuthUser;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.homework.adapter.in.web.GetCalendarFrequencyController;
import com.noti.noti.homework.application.port.in.FilteredHomeworkCommand;
import com.noti.noti.homework.application.port.in.GetFilteredHomeworkQuery;
import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import com.noti.noti.lesson.application.port.in.DateFrequencyOfLessons;
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
@WebMvcTest(controllers = GetCalendarFrequencyController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = CustomJwtFilter.class))
class GetFrequencyOfLessonsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetFrequencyOfLessonsQuery getFrequencyOfLessonsQuery;

  @MockBean
  private GetFilteredHomeworkQuery getFilteredHomeworkQuery;


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


  List<InFilteredHomeworkFrequency> createInFilteredHomeworkFrequency() {
    InFilteredHomeworkFrequency inFilteredHomeworkFrequency1 = new InFilteredHomeworkFrequency(new OutFilteredHomeworkFrequency(
        LocalDateTime.now(), 1L));
    InFilteredHomeworkFrequency inFilteredHomeworkFrequency2 = new InFilteredHomeworkFrequency(new OutFilteredHomeworkFrequency(
        LocalDateTime.now(), 2L));
    InFilteredHomeworkFrequency inFilteredHomeworkFrequency3 = new InFilteredHomeworkFrequency(new OutFilteredHomeworkFrequency(
        LocalDateTime.now(), 3L));
    InFilteredHomeworkFrequency inFilteredHomeworkFrequency4 = new InFilteredHomeworkFrequency(new OutFilteredHomeworkFrequency(
        LocalDateTime.now(), 4L));
    return List.of(inFilteredHomeworkFrequency1, inFilteredHomeworkFrequency2, inFilteredHomeworkFrequency3, inFilteredHomeworkFrequency4);
  }

  List<InFilteredHomeworkFrequency> notCreateInFilteredHomeworkFrequency() {
    return List.of();
  }


  @Nested
  class 수업_빈도_조회_응답 {

    @Nested
    class 날짜_형식을_제대로_전달할_때 {

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 주어진_날짜에_대한_날짜내용과_분반_수_반환() throws Exception {
        when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(Integer.class), any(Integer.class), any(Long.class)))
            .thenReturn(createLessons());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "12", "all"))) // ex. /api/teacher/calendar/2023/1
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE))
            .andExpect(jsonPath("$.data.length()").value(4));
      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 빈_리스트_반환() throws Exception {
        when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(Integer.class), any(Integer.class), any(Long.class)))
            .thenReturn(notCreateLessons());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "12", "all")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE))
            .andExpect(jsonPath("$.data.length()").value(0));

      }

    }

    @Nested
    class 날짜_형식을_제대로_전달하지_않았을_때 {

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 월이_12를_초과할_경우_예외코드_400발생() throws Exception {
        when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(Integer.class), any(Integer.class), any(Long.class)))
            .thenReturn(notCreateLessons());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "13", "all")))
            .andExpect(status().is(400));

      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 월이_1를_미만일_경우_예외코드_400발생() throws Exception {
        when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(Integer.class), any(Integer.class), any(Long.class)))
            .thenReturn(notCreateLessons());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "0", "all")))
            .andExpect(status().is(400));

      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 년을_잘못_전달_시_예외코드_400발생() throws Exception {
        when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(Integer.class), any(Integer.class), any(Long.class)))
            .thenReturn(notCreateLessons());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("-1", "2", "all")))
            .andExpect(status().is(400));

      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 타입을_잘못_전달_시_예외코드_400발생() throws Exception {
        when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(Integer.class), any(Integer.class), any(Long.class)))
            .thenReturn(notCreateLessons());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "JULY", "all")))
            .andExpect(status().is(400));

      }
    }

    @Nested
    class 선생님_id가_제대로_전달되지_않았을_때 {

      @Test
      void 예외코드_401발생() throws Exception {
        when(getFrequencyOfLessonsQuery.findFrequencyOfLessons(any(Integer.class), any(Integer.class), any(Long.class)))
            .thenReturn(createLessons());
        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "2", "all")))
            .andExpect(status().is(401));

      }

    }
  }

  @Nested
  class 숙제_빈도_조회_응답 {

    @Nested
    class 날짜_형식을_제대로_전달할_때 {

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 주어진_날짜에_대한_날짜내용과_분반_수_반환() throws Exception {

        when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
            .thenReturn(createInFilteredHomeworkFrequency());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "12", "1"))) // ex. /api/teacher/calendar/2023/1
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE))
            .andExpect(jsonPath("$.data.length()").value(4));
      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 빈_리스트_반환() throws Exception {
        when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
            .thenReturn(notCreateInFilteredHomeworkFrequency());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "12", "1")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE))
            .andExpect(jsonPath("$.data.length()").value(0));

      }

    }

    @Nested
    class 날짜_형식을_제대로_전달하지_않았을_때 {

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 월이_12를_초과할_경우_예외코드_400발생() throws Exception {
        when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
            .thenReturn(createInFilteredHomeworkFrequency());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "13", "1")))
            .andExpect(status().is(400));

      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 월이_1를_미만일_경우_예외코드_400발생() throws Exception {
        when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
            .thenReturn(createInFilteredHomeworkFrequency());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "0", "1")))
            .andExpect(status().is(400));

      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 년을_잘못_전달_시_예외코드_400발생() throws Exception {
        when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
            .thenReturn(createInFilteredHomeworkFrequency());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("-1", "2", "1")))
            .andExpect(status().is(400));

      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 타입을_잘못_전달_시_예외코드_400발생() throws Exception {
        when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
            .thenReturn(createInFilteredHomeworkFrequency());

        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "JULY", "1")))
            .andExpect(status().is(400));

      }
    }

    @Nested
    class 선생님_id가_제대로_전달되지_않았을_때 {

      @Test
      void 예외코드_401발생() throws Exception {
        when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
            .thenReturn(createInFilteredHomeworkFrequency());
        mockMvc.perform(get("/api/teacher/calendar").params(createInfo("2023", "2", "1")))
            .andExpect(status().is(401));

      }

    }


  }


  private MultiValueMap<String, String> createInfo(String year, String month, String lessonType) {
    MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
    info.add("year", String.valueOf(year));
    info.add("month", String.valueOf(month));
    info.add("lessonType", String.valueOf(lessonType));
    return info;
  }

}