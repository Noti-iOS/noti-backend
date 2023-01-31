package com.noti.noti.studenthomework.adapter.in.web.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.noti.noti.common.WithAuthUser;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.studenthomework.application.port.in.GetHomeworksOfCalendarQuery;
import com.noti.noti.studenthomework.application.port.in.InHomeworkOfGivenDate;
import com.noti.noti.studenthomework.application.port.out.OutHomeworkOfGivenDate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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

@WebMvcTest(GetHomeworksOfCalendarController.class)
@Import(JacksonConfiguration.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class GetHomeworksOfCalendarControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  GetHomeworksOfCalendarQuery getHomeworksOfCalendarQuery;

  @MockBean
  JwtTokenProvider provider;

  List<InHomeworkOfGivenDate> createHomeworkOfCalendar() {

    OutHomeworkOfGivenDate.HomeworkDto homework1 = new OutHomeworkOfGivenDate.HomeworkDto(1L, "content1", 20L, 10L);
    OutHomeworkOfGivenDate.HomeworkDto homework2 = new OutHomeworkOfGivenDate.HomeworkDto(2L, "content2", 5L, 1L);
    OutHomeworkOfGivenDate.HomeworkDto homework3 = new OutHomeworkOfGivenDate.HomeworkDto(3L, "content3", 14L, 14L);
    OutHomeworkOfGivenDate.HomeworkDto homework4 = new OutHomeworkOfGivenDate.HomeworkDto(4L, "content4", 20L, 16L);


    final LocalTime startTime = LocalTime.now();
    final LocalTime endTime = LocalTime.now();

    InHomeworkOfGivenDate lesson1 = new InHomeworkOfGivenDate(1L, "lesson1", startTime, endTime,
        List.of(homework1, homework2));
    InHomeworkOfGivenDate lesson2 = new InHomeworkOfGivenDate(2L, "lesson2", startTime, endTime,
        List.of(homework3));
    InHomeworkOfGivenDate lesson3 = new InHomeworkOfGivenDate(3L, "lesson3", startTime, endTime,
        List.of(homework4));

    return List.of(lesson3, lesson2, lesson1);
  }


  List<InHomeworkOfGivenDate> notCreateHomeworkOfCalendar() {

    return List.of();
  }


  @Nested
  class 선생님과_날짜에_해당하는_숙제가_있다면 {

    @Test
    @WithAuthUser(id = "1", role = "TEACHER")
    void 응답코드_200과_숙제_목록을_반환한다() throws Exception {
      when(getHomeworksOfCalendarQuery.findHomeworksOfCalendar(any(LocalDate.class), anyLong()))
          .thenReturn(createHomeworkOfCalendar());

      LocalDate now = LocalDate.now();
      String year = Integer.toString(now.getYear());
      String month = Integer.toString(now.getMonth().getValue());
      String day = Integer.toString(now.getDayOfMonth());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/{year}/{month}/{day}", year, month, day))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lessonId").value(3L))
          .andExpect(MockMvcResultMatchers.jsonPath("$.[2].lessonName").value("lesson1"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.[2].homeworks.length()").value(2))
          .andExpect(MockMvcResultMatchers.jsonPath("$.[1].homeworks.length()").value(1));
    }

  }

  @Nested
  class 선생님은_있지만_날짜에_해당하는_숙제가_없다면 {

    @Test
    @WithAuthUser(id = "1", role = "TEACHER")
    void 응답코드_200과_빈_리스트를_반환한다() throws Exception {
      when(getHomeworksOfCalendarQuery.findHomeworksOfCalendar(any(LocalDate.class), anyLong()))
          .thenReturn(notCreateHomeworkOfCalendar());

      LocalDate now = LocalDate.now().plusDays(2);
      String year = Integer.toString(now.getYear());
      String month = Integer.toString(now.getMonth().getValue());
      String day = Integer.toString(now.getDayOfMonth());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/{year}/{month}/{day}", year, month, day))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

  }

  @Nested
  class 선생님은_있지만_넘긴_날짜_값이_유효하지_않다면 {

    @Test
    @WithAuthUser(id = "1", role = "TEACHER")
    void 응답코드_400을_반환한다() throws Exception {
      when(getHomeworksOfCalendarQuery.findHomeworksOfCalendar(any(LocalDate.class), anyLong()))
          .thenReturn(notCreateHomeworkOfCalendar());

      LocalDate now = LocalDate.now();
      String year = Integer.toString(now.getYear());
      String month = now.getMonth().toString();
      String day = now.getDayOfWeek().toString();

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/{year}/{month}/{day}", year, month, day)) // ex. /api/teacher/calendar/2023/JANUARY/TUESDAY
          .andExpect(MockMvcResultMatchers.status().is(400));

    }

  }

  @Nested
  class 해당하는_선생님이_없다면 {

    @Test
    void 응답코드_401을_반환한다() throws Exception {
      when(getHomeworksOfCalendarQuery.findHomeworksOfCalendar(any(LocalDate.class), anyLong()))
          .thenReturn(createHomeworkOfCalendar());

      LocalDate now = LocalDate.now();
      String year = Integer.toString(now.getYear());
      String month = Integer.toString(now.getMonth().getValue());
      String day = Integer.toString(now.getDayOfMonth());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/{year}/{month}/{day}", year, month, day))
          .andExpect(MockMvcResultMatchers.status().is(401));

    }


  }


}