package com.noti.noti.notification.adapter.in.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.common.WithAuthUser;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.notification.adapter.in.web.dto.request.SaveFcmTokenRequest;
import com.noti.noti.notification.application.port.in.SaveFcmTokenUsecase;
import com.noti.noti.notification.domain.model.FcmToken;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;


@WebMvcTest(controllers = SaveFcmTokenController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = CustomJwtFilter.class))
@DisplayName("SaveFcmTokenControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
class SaveFcmTokenControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  SaveFcmTokenUsecase saveFcmTokenUsecase;

  @Autowired
  ObjectMapper objectMapper;

  @Nested
  class saveFcmToken_메서드는 {

    @Nested
    class fcmToken_key가_없는_요청이_주어지면 {

      final String EMPTY_FCM_TOKEN_REQUEST = "{}";

      @WithAuthUser(id = "1", role = "ROLE_TEACHER")
      @Test
      void 응답코드_400_에러가_발생한다() throws Exception {

        mockMvc.perform(post("/api/notification/tokens")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(EMPTY_FCM_TOKEN_REQUEST))
            .andExpectAll(
                status().isBadRequest(),
                result -> assertThat(result.getResolvedException()).isInstanceOf(
                    MethodArgumentNotValidException.class)
            );
      }
    }

    @Nested
    class value가_공백인_요청이_주어지면 {

      final String WHITE_SPACE_REQUEST =
          "{"
              + "\"fcmToken\": \"\""
              + "}";

      @WithAuthUser(id = "1", role = "ROLE_TEACHER")
      @Test
      void 응답코드_400_에러가_발생한다() throws Exception {

        mockMvc.perform(post("/api/notification/tokens")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(WHITE_SPACE_REQUEST))
            .andExpectAll(
                status().isBadRequest(),
                result -> assertThat(result.getResolvedException()).isInstanceOf(
                    MethodArgumentNotValidException.class)
            );
      }
    }

    @Nested
    class 인증이_되지_않은_유저의_요청이_주어지면 {

      @Test
      void 응답코드_401_에러가_발생한다() throws Exception {
        mockMvc.perform(post("/api/notification/tokens")
                .with(csrf()))
            .andExpect(status().isUnauthorized());
      }
    }

    @Nested
    class 올바른_요청이_주어지면 {

      SaveFcmTokenRequest createValidRequest() {
        return MonkeyUtils.MONKEY.giveMeOne(SaveFcmTokenRequest.class);
      }

      @WithAuthUser(id = "1", role = "ROLE_TEACHER")
      @Test
      void 성공적으로_요청을_수행하고_201_응답을_반환한다() throws Exception {
        SaveFcmTokenRequest givenRequest = createValidRequest();

        FcmToken returnedFcmToken = FcmToken.builder()
            .fcmToken(givenRequest.getFcmToken())
            .userId(1L)
            .build();

        when(saveFcmTokenUsecase.apply(any())).thenReturn(returnedFcmToken);

        mockMvc.perform(post("/api/notification/tokens")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(givenRequest)))
            .andExpect(status().isCreated());
      }
    }
  }
}