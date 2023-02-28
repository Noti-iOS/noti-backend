package com.noti.noti.homework.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;

import com.noti.noti.common.WithAuthUser;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.homework.application.port.in.FilteredHomeworkCommand;
import com.noti.noti.homework.application.port.in.GetFilteredHomeworkQuery;
import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import com.noti.noti.lesson.adapter.in.web.controller.GetCreatedLessonsController;
import java.time.LocalDate;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("GetFilteredHomeworkControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
@WebMvcTest(GetFilteredHomeworkController.class)
class GetFilteredHomeworkControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @MockBean
  private GetFilteredHomeworkQuery getFilteredHomeworkQuery;


  List<InFilteredHomeworkFrequency> createInFilteredHomeworkFrequency() {
    OutFilteredHomeworkFrequency out1 = new OutFilteredHomeworkFrequency(
        LocalDate.of(2022, 2, 3), 2);
    OutFilteredHomeworkFrequency out2 = new OutFilteredHomeworkFrequency(LocalDate.of(2022, 2, 7), 3);
    OutFilteredHomeworkFrequency out3 =new OutFilteredHomeworkFrequency(LocalDate.of(2022, 2, 10), 4);

    InFilteredHomeworkFrequency in1 = new InFilteredHomeworkFrequency(out1);
    InFilteredHomeworkFrequency in2 = new InFilteredHomeworkFrequency(out1);
    InFilteredHomeworkFrequency in3 = new InFilteredHomeworkFrequency(out1);


    return List.of(in1, in2, in3);
  }

  List<InFilteredHomeworkFrequency> createEmptyInFilteredHomeworkFrequency() {
    return List.of();
  }

  @Nested
  class getFilteredHomeworkInfo_메소드는 {

    @Nested
    class 유효한_선생님Id과_수업Id를_전달하면_응답코드_200 {

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 선생님이_수업을_생성했다면_해당리스트_반환() throws Exception {
        Mockito.when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
            .thenReturn(createInFilteredHomeworkFrequency());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/filter"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3));
      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 선생님이_생성한_수업이_없다면_빈리스트_반환() throws Exception {
        Mockito.when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
            .thenReturn(createEmptyInFilteredHomeworkFrequency());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/filter"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(SuccessResponse.SUCCESS_MESSAGE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()").value(0));
      }

    }

    @Test
    void 유효하지_않는_선생님Id_라면_응답코드_401() throws Exception {
      Mockito.when(getFilteredHomeworkQuery.getFilteredHomeworks(any(FilteredHomeworkCommand.class)))
          .thenReturn(createEmptyInFilteredHomeworkFrequency());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/filter"))
          .andExpect(MockMvcResultMatchers.status().is(401));
    }

  }


}