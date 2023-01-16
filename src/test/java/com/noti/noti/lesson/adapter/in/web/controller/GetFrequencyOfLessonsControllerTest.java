package com.noti.noti.lesson.adapter.in.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.homework.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.homework.adapter.out.persistence.HomeworkPersistenceAdapter;
import com.noti.noti.lesson.application.port.in.GetTodaysLessonQuery;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@DisplayName("GetFrequencyOfLessonsControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(GetFrequencyOfLessonsController.class)
@ContextConfiguration(classes = GetFrequencyOfLessonsController.class)
class GetFrequencyOfLessonsControllerTest {


  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @MockBean
  private GetTodaysLessonQuery getTodaysLessonQuery;

  @Autowired
  private HomeworkPersistenceAdapter homeworkPersistenceAdapter;

  private GetFrequencyOfLessonsController getFrequencyOfLessonsController;




  // 년-월 형식으로 제대로 줄 때
      // 제대로 분반-개수 반환
      // 빈 리스트 반환
      // 서버 에러
  // 형식이 맞지 않을 때 년-, 년-월-일
      // 클라이언트 쪽 예외 발생

  // 함수 - get 방식임 그 외에는 예외처리(클라이언트 문제)

  /*
  @Test
    public void testGetProduct() throws Exception {
        mockMvc.perform(get("/products/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ProductController.class))
                .andExpect(handler().methodName("getProduct"))
                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.productName", is("과자")))
                .andExpect(jsonPath("$.price", is(105.5)))
                .andDo(MockMvcResultHandlers.print());
    }
   */
  @Nested
  class 날짜_형식을_제대로_전달할_때 {

    //@GetMapping("/api/teacher/calendar/{year}/{month}")

    String yearMonth = "2023-1";
    @Sql("/data/homework-lesson.sql")
    @Test
    void 주어진_날짜에_대한_날짜내용과_분반_수_반환() throws Exception {

//      System.out.println(wac.getBean(GetFrequencyOfLessonsController.class));
////      System.out.println(wac.getBean(HomeworkPersistenceAdapter.class));
//
//      /*mockMvc.perform(get("/api/teacher/calendar/2023/1")
//              .accept(MediaType.APPLICATION_JSON))
//          .andExpect(status().isOk())
////          .andExpect(handler().handlerType(GetFrequencyOfLessonsController.class))
//          .andExpect(handler().methodName("getFrequencyOfLessons"))
//          .andDo(MockMvcResultHandlers.print());*/
    }

    @Test
    void 빈_리스트_반환() {

    }

  }

  @Nested
  @Sql("/data/homework-lesson.sql")
  class 날짜_형식을_제대로_전달하지_않았을_때 {

    @Test
    void 예외코드_400발생() {

    }

  }

}