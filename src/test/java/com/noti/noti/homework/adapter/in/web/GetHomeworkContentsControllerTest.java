package com.noti.noti.homework.adapter.in.web;

import static com.noti.noti.common.MonkeyUtils.MONKEY;
import static net.jqwik.api.Arbitraries.integers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.noti.noti.common.WithAuthUser;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.homework.application.port.in.GetHomeworkContentQuery;
import com.noti.noti.homework.application.port.in.HomeworkContentCommand;
import com.noti.noti.homework.application.port.in.InHomeworkContent;
import com.noti.noti.lesson.exception.LessonNotFoundException;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@DisplayName("GetHomeworkContentsControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
@WebMvcTest(controllers = GetHomeworkContentsController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = CustomJwtFilter.class))
class GetHomeworkContentsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetHomeworkContentQuery getHomeworkContentQuery;


  @Nested
  class getHomeworkContentInfo_메서드는 {

    @Nested
    class 파라미터_조건이_유효하면_응답코드_200 {

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      public void 해당_수업이_있으면_숙제목록_리스트_반환() throws Exception {
        //given
        List<InHomeworkContent> inHomeworkContents = createListRandomSizeBetween(1, 10);

        //when
        Mockito.when(getHomeworkContentQuery.getHomeworkContents(Mockito.any(HomeworkContentCommand.class)))
            .thenReturn(inHomeworkContents);

        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/teacher/calendar/filtered/content")
                    .params(createInfo(integers().greaterOrEqual(1).sample().toString(), MONKEY.giveMeOne(LocalDate.class).toString())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data.size()").value(inHomeworkContents.size()));

      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      public void 해당_수업이_없다면_빈_리스트_반환() throws Exception {
        // given
        List<InHomeworkContent> inHomeworkContents = createListRandomSizeBetween(0, 0);

        //when
        Mockito.when(
                getHomeworkContentQuery.getHomeworkContents(Mockito.any(HomeworkContentCommand.class)))
            .thenReturn(inHomeworkContents);

        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/teacher/calendar/filtered/content")
                    .params(createInfo(integers().greaterOrEqual(1).sample().toString(), MONKEY.giveMeOne(LocalDate.class).toString())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data.size()").value(0));
      }

    }


    @Nested
    class 파라미터_조건이_유효하지_않을_때_응답코드_4XX {

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      public void 존재하지_않는_날짜일_때_응답코드_400() throws Exception {
        //given
        List<InHomeworkContent> inHomeworkContents = createListRandomSizeBetween(1, 10);

        //when
        Mockito.when(
                getHomeworkContentQuery.getHomeworkContents(Mockito.any(HomeworkContentCommand.class)))
            .thenReturn(inHomeworkContents);

        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/teacher/calendar/filtered/content")
                    .params(createInfo(integers().greaterOrEqual(1).sample().toString(), "2023-02-30")))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("올바르지 않은 입력 값입니다"))
            .andExpect(jsonPath("$.code").value("C001"));
      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      public void 날짜_형식이_다를_때_응답코드_400() throws Exception {
        //given
        List<InHomeworkContent> inHomeworkContents = createListRandomSizeBetween(1, 10);

        //when
        Mockito.when(
                getHomeworkContentQuery.getHomeworkContents(Mockito.any(HomeworkContentCommand.class)))
            .thenReturn(inHomeworkContents);

        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/teacher/calendar/filtered/content")
                    .params(createInfo(integers().greaterOrEqual(1).sample().toString(), "2023:02:10")))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("올바르지 않은 입력 값입니다"))
            .andExpect(jsonPath("$.code").value("C001"));
      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      public void 존재하지_않는_수업일_때_응답코드_404() throws Exception {
        //given
        Mockito.when(
                getHomeworkContentQuery.getHomeworkContents(Mockito.any(HomeworkContentCommand.class)))
            .thenThrow(new LessonNotFoundException(MONKEY.giveMeOne(Long.class)));

        //when
        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/teacher/calendar/filtered/content")
                    .params(createInfo(integers().greaterOrEqual(1).sample().toString(), "2023-02-22")))
            .andExpect(status().is(404))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("해당 수업 정보가 존재하지 않습니다"))
            .andExpect(jsonPath("$.code").value("L001"));
      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      public void 날짜값이_null일_때_응답코드_400() throws Exception {
        //given
        List<InHomeworkContent> inHomeworkContents = createListRandomSizeBetween(1, 10);

        //when
        Mockito.when(
                getHomeworkContentQuery.getHomeworkContents(Mockito.any(HomeworkContentCommand.class)))
            .thenReturn(inHomeworkContents);

        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/teacher/calendar/filtered/content")
                    .params(createInfo(integers().greaterOrEqual(1).sample().toString(), null)))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("올바르지 않은 입력 값입니다"))
            .andExpect(jsonPath("$.code").value("C001"));

      }

      @Test
      @WithAuthUser(id = "1", role = "TEACHER")
      public void 수업id가_null일_때_응답코드_4001() throws Exception {
        //given
        List<InHomeworkContent> inHomeworkContents = createListRandomSizeBetween(1, 10);

        //when
        Mockito.when(
                getHomeworkContentQuery.getHomeworkContents(Mockito.any(HomeworkContentCommand.class)))
            .thenReturn(inHomeworkContents);

        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/teacher/calendar/filtered/content")
                    .params(createInfo(null, MONKEY.giveMeOne(LocalDate.class).toString())))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("올바르지 않은 입력 값입니다"))
            .andExpect(jsonPath("$.code").value("C001"));
      }

      @Test
      public void 선생님_id가_존재하지_않을_때_응답코드_401() throws Exception {
        //given
        List<InHomeworkContent> inHomeworkContents = createListRandomSizeBetween(1, 10);

        //when
        Mockito.when(
                getHomeworkContentQuery.getHomeworkContents(Mockito.any(HomeworkContentCommand.class)))
            .thenReturn(inHomeworkContents);

        //TODO: body ㄴ
        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/teacher/calendar/filtered/content")
                    .params(createInfo("1", "2022-03-09")))
            .andExpect(status().is(401));
      }

    }


  }

  private MultiValueMap<String, String> createInfo(String lessonId, String date) {
    MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
    info.add("lessonId", lessonId);
    info.add("date", date);
    return info;
  }

  private List<InHomeworkContent> createListRandomSizeBetween(int min, int max) {
    Integer size = integers().between(min, max).sample();
    return MONKEY.giveMeBuilder(InHomeworkContent.class).sampleList(size);
  }






}