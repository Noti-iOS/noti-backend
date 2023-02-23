package com.noti.noti.book.adapter.in.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.noti.noti.book.application.port.in.GetAllBooksQuery;
import com.noti.noti.book.application.port.out.BookDto;
import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.common.WithAuthUser;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import java.util.List;
import net.jqwik.api.Arbitraries;
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

@WebMvcTest(controllers = GetAllBooksController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = CustomJwtFilter.class))
@DisplayName("GetAllBooksController 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
class GetAllBooksControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  GetAllBooksQuery getAllBooksQuery;

  @WithAuthUser(id = "1", role = "ROLE_TEACHER")
  @Nested
  class getAllBooks_메서드는 {

    @Nested
    class 요청에_해당하는_교재_정보가_존재하지_않으면 {

      @Test
      void 비어있는_Response_List를_반환한다() throws Exception {
        when(getAllBooksQuery.getAllBooks(anyLong())).thenReturn(List.of());

        mockMvc.perform(get("/api/teacher/books"))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.data").isEmpty()
            );
      }
    }

    @Nested
    class 요청에_해당하는_교재_정보가_존재하면 {

      @Test
      void 교재_정보가_담긴_Response_List를_반환한다() throws Exception {
        List<BookDto> givenBookDtos = MonkeyUtils.MONKEY.giveMeBuilder(BookDto.class)
            .set("id", Arbitraries.longs().greaterOrEqual(1L))
            .setNotNull("title")
            .sampleList(5);

        when(getAllBooksQuery.getAllBooks(anyLong())).thenReturn(givenBookDtos);
        mockMvc.perform(get("/api/teacher/books"))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.data").isNotEmpty()
            );
      }
    }

    @Nested
    class 요청에_해당하는_선생_정보가_존재하지_않으면 {

      @Test
      void TeacherNotFoundException_예외가_발생하고_404_응답을_반환한다() throws Exception {
        when(getAllBooksQuery.getAllBooks(anyLong())).thenThrow(new TeacherNotFoundException());
        mockMvc.perform(get("/api/teacher/books"))
            .andExpectAll(
                status().isNotFound(),
                result -> assertThat(result.getResolvedException()).isInstanceOf(
                    TeacherNotFoundException.class)
            );
      }
    }
  }
}