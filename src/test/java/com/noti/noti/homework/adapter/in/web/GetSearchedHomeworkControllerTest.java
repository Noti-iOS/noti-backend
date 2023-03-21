package com.noti.noti.homework.adapter.in.web;

import static com.noti.noti.common.MonkeyUtils.MONKEY;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.common.WithAuthUser;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.homework.adapter.in.web.dto.request.SearchedHomeworkRequest;
import com.noti.noti.homework.application.port.in.GetSearchedHomeworkQuery;
import com.noti.noti.homework.application.port.in.InSearchedPageDto;
import com.noti.noti.homework.application.port.in.InSearchedPageDto.InSearchedHomework;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@DisplayName("GetSearchedHomeworkControllerTest 클래스")
@WebMvcTest(controllers = GetSearchedHomeworkController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CustomJwtFilter.class))
@Import(JacksonConfiguration.class)
class GetSearchedHomeworkControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  GetSearchedHomeworkQuery getSearchedHomeworkQuery;


  @Nested
  class getSearchedHomeworkInfo_메소드는 {

    @Nested
    class 조건이_유효하면_응답코드_200 {

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 조건에_해당하는_숙제목록이_존재하면_해당_리스트_반환() throws Exception {
        when(getSearchedHomeworkQuery.getSearchedHomeworks(anyLong(), anyString(), anyString(), anyInt()))
            .thenReturn(createInSearchedPageDto("1", createInSearchedList(3), false));

        byte[] content = objectMapper.writeValueAsBytes(new SearchedHomeworkRequest(1L, "", 3, "1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/search")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(SuccessResponse.SUCCESS_MESSAGE))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data.searchedHomeworks.size()").value(3))
            .andExpect(jsonPath("$.data.nextCursorId").isNotEmpty());
      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      void 조건에_해당하는_숙제목록이_없다면_searchedHomeworks가_빈_리스트_반환() throws Exception {

        when(getSearchedHomeworkQuery.getSearchedHomeworks(anyLong(), anyString(), anyString(), anyInt()))
            .thenReturn(createInSearchedPageDto(null, createInSearchedList(0), false));

        byte[] content = objectMapper.writeValueAsBytes(new SearchedHomeworkRequest(1L, "", 0, "1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/search")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(SuccessResponse.SUCCESS_MESSAGE))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data.searchedHomeworks.size()").value(0))
            .andExpect(jsonPath("$.data.nextCursorId").doesNotExist());
      }

    }



    @Nested
    class 파라미터를_잘못_전달하면_4XX {
      @Test
      void 인증되지_않은_선생님이_전달되면_응답코드_401() throws Exception {
        when(getSearchedHomeworkQuery.getSearchedHomeworks(anyLong(), anyString(), anyString(), anyInt()))
            .thenReturn(createInSearchedPageDto(null, createInSearchedList(0), false));

        byte[] content = objectMapper.writeValueAsBytes(new SearchedHomeworkRequest(1L, "", 0, "1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/calendar/search")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(401));
      }

    }

  }


  private List<InSearchedHomework> createInSearchedList(int size) {
    return MONKEY.giveMeBuilder(InSearchedHomework.class)
        .setNotNull("homeworkName")
        .setNotNull("lessonName")
        .setNotNull("startTime")
        .setNotNull("endTime")
        .setNotNull("startDate")
        .sampleList(size);
  }

  private InSearchedPageDto createInSearchedPageDto(String cursorId, List<InSearchedHomework> inSearchedHomeworks, Boolean last) {
    return MONKEY.giveMeBuilder(InSearchedPageDto.class)
        .set("nextCursorId", cursorId)
        .set("searchedHomeworks", inSearchedHomeworks)
        .set("last", last)
        .sample();
  }

}


