package com.noti.noti.notification.adapter.in.web.controller;

import static com.noti.noti.error.ErrorCode.FCM_TOKEN_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.noti.noti.error.GlobalExceptionHandler;
import com.noti.noti.notification.application.port.in.UpdateFcmTokenUsecase;
import com.noti.noti.notification.exception.FcmTokenNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("UpdateFcmTokenControllerTest 클래스")
class UpdateFcmTokenControllerTest {

  @InjectMocks
  private UpdateFcmTokenController updateFcmTokenController;

  @Mock
  private UpdateFcmTokenUsecase updateFcmTokenUsecase;
  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(updateFcmTokenController)
        .setControllerAdvice(GlobalExceptionHandler.class)
        .addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
  }

  @Nested
  class updateFcmToken_메서드는 {

    final String VALID_REQUEST = "{\"fcmToken\":\"ID\"}";

    @Nested
    class 요청에_해당하는_정보가_존재하면 {

      @Test
      void 성공적으로_갱신하고_200_응답을_반환한다() throws Exception {

        doNothing().when(updateFcmTokenUsecase).updateFcmToken(any());

        mockMvc.perform(patch("/api/notification/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_REQUEST))
            .andExpect(status().isOk());
      }
    }

    @Nested
    class 요청의_body가_비어있으면 {

      final String INVALID_REQUEST = "{}";

      @Test
      void FcmTokenNotFoundException_예외가_발생하고_400_응답을_반환한다() throws Exception {
        mockMvc.perform(patch("/api/notification/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_REQUEST))
            .andExpectAll(
                status().isBadRequest(),
                result -> {
                  assertAll(
                      () -> verify(updateFcmTokenUsecase, never()).updateFcmToken(any()),
                      () -> assertThat(result.getResolvedException())
                          .isInstanceOf(MethodArgumentNotValidException.class)
                  );
                }
            );
      }
    }

    @Nested
    class 요청에_해당하는_정보가_존재하지_않으면 {

      @Test
      void MethodArgumentNotValidException_예외가_발생하고_404_응답을_반환한다() throws Exception {

        doThrow(new FcmTokenNotFoundException()).when(updateFcmTokenUsecase)
            .updateFcmToken(any());

        mockMvc.perform(patch("/api/notification/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_REQUEST))
            .andExpectAll(
                status().isNotFound(),
                jsonPath("$.message").value(FCM_TOKEN_NOT_FOUND.getMessage()),
                result -> {
                  assertThat(result.getResolvedException())
                      .isInstanceOf(FcmTokenNotFoundException.class);
                }
            );

      }
    }

    @Nested
    class 요청의_fcmToken_value가_비어있으면 {

      final String INVALID_REQUEST = "{\"fcmToken\":\"\"}";

      @Test
      void MethodArgumentNotValidException_예외가_발생하고_400_응답을_반환한다() throws Exception {
        mockMvc.perform(patch("/api/notification/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_REQUEST))
            .andExpectAll(
                status().isBadRequest(),
                result -> {
                  assertAll(
                      () -> verify(updateFcmTokenUsecase, never()).updateFcmToken(any()),
                      () -> assertThat(result.getResolvedException())
                          .isInstanceOf(MethodArgumentNotValidException.class)
                  );
                }
            );
      }
    }
  }
}