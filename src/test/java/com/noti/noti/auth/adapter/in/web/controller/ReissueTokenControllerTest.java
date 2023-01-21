package com.noti.noti.auth.adapter.in.web.controller;

import static com.noti.noti.error.ErrorCode.EXPIRED_JWT;
import static com.noti.noti.error.ErrorCode.ILLEGAL_ARGUMENT_JWT;
import static com.noti.noti.error.ErrorCode.INVALID_SIGNATURE_JWT;
import static com.noti.noti.error.ErrorCode.MALFORMED_JWT;
import static com.noti.noti.error.ErrorCode.TEACHER_NOT_FOUND;
import static com.noti.noti.error.ErrorCode.UNSUPPORTED_JWT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.noti.noti.auth.application.port.in.ReissueTokenUsecace;
import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.error.GlobalExceptionHandler;
import com.noti.noti.error.exception.CustomExpiredJwtException;
import com.noti.noti.error.exception.CustomIllegalArgumentException;
import com.noti.noti.error.exception.CustomMalformedJwtException;
import com.noti.noti.error.exception.CustomSignatureException;
import com.noti.noti.error.exception.CustomUnsupportedJwtException;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReissueTokenControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ReissueTokenControllerTest {

  @InjectMocks
  ReissueTokenController reissueTokenController;

  @Mock
  ReissueTokenUsecace reissueTokenUsecace;

  MockMvc mockMvc;

  // MockMvc 초기화 메서드 객 테스트 실행전에 실행된다.
  @BeforeEach
  void prepare() {
    mockMvc = MockMvcBuilders.standaloneSetup(reissueTokenController)
        .setControllerAdvice(GlobalExceptionHandler.class)
        .addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
  }

  @Nested
  class reissueTokne_메서드는 {

    final String TOKEN = "token";
    final String AUTHORIZATION_HEADER = "Authorization";
    final String BEARER_PREFIX = "Bearer ";

    @Nested
    class 요청_헤더에_올바른_토큰이_주어지면 {

      final String ACCESS_TOKEN = "access token";
      final String REFRESH_TOKEN = "refresh token";
      final JwtToken jwtToken = new JwtToken(ACCESS_TOKEN, REFRESH_TOKEN);

      @Test
      void 성공적으로_토큰을_갱신하고_토큰이_담긴_응답_객체를_반환한다() throws Exception {
        when(reissueTokenUsecace.reissueToken(TOKEN)).thenReturn(jwtToken);

        mockMvc.perform(
                get("/api/auth/reissue").header(AUTHORIZATION_HEADER, BEARER_PREFIX + TOKEN))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.accessToken").value(ACCESS_TOKEN),
                jsonPath("$.refreshToken").value(REFRESH_TOKEN));
      }
    }

    @Nested
    class 요청_헤더에_만료된_토큰이_주어지면 {

      @Test
      void CustomExpiredJwtException_예외가_발생하고_401_에러응답_객체를_반환한다() throws Exception {
        when(reissueTokenUsecace.reissueToken(TOKEN))
            .thenThrow(new CustomExpiredJwtException("만료된 토큰입니다"));

        mockMvc.perform(
                get("/api/auth/reissue")
                    .header(AUTHORIZATION_HEADER, BEARER_PREFIX + TOKEN))
            .andExpectAll(
                status().isUnauthorized(),
                result -> assertThat(result.getResolvedException())
                    .isInstanceOf(CustomExpiredJwtException.class),
                jsonPath("$.message").value(EXPIRED_JWT.getMessage()),
                jsonPath("$.status").value(EXPIRED_JWT.getStatus()),
                jsonPath("$.errors").isEmpty(),
                jsonPath("$.code").value(EXPIRED_JWT.getCode())
            );
      }
    }

    @Nested
    class 요청_헤더에_지원하지_않는_토큰이_주어지면 {

      @Test
      void CustomUnsupportedJwtException_예외가_발생하고_401_에러응답_객체를_반환한다() throws Exception {
        when(reissueTokenUsecace.reissueToken(TOKEN))
            .thenThrow(new CustomUnsupportedJwtException("지원하지 않는 토큰입니다"));

        mockMvc.perform(
                get("/api/auth/reissue")
                    .header(AUTHORIZATION_HEADER, BEARER_PREFIX + TOKEN))
            .andExpectAll(
                status().isUnauthorized(),
                result -> assertThat(result.getResolvedException())
                    .isInstanceOf(CustomUnsupportedJwtException.class),
                jsonPath("$.message").value(UNSUPPORTED_JWT.getMessage()),
                jsonPath("$.status").value(UNSUPPORTED_JWT.getStatus()),
                jsonPath("$.errors").isEmpty(),
                jsonPath("$.code").value(UNSUPPORTED_JWT.getCode())
            );
      }
    }

    @Nested
    class 요청_헤더에_잘못된_토큰이_주어지면 {

      @Test
      void CustomMalformedJwtException_예외가_발생하고_401_에러응답_객체를_반환한다() throws Exception {
        when(reissueTokenUsecace.reissueToken(TOKEN))
            .thenThrow(new CustomMalformedJwtException("잘못된 토큰입니다"));

        mockMvc.perform(
                get("/api/auth/reissue")
                    .header(AUTHORIZATION_HEADER, BEARER_PREFIX + TOKEN))
            .andExpectAll(
                status().isUnauthorized(),
                result -> assertThat(result.getResolvedException())
                    .isInstanceOf(CustomMalformedJwtException.class),
                jsonPath("$.message").value(MALFORMED_JWT.getMessage()),
                jsonPath("$.status").value(MALFORMED_JWT.getStatus()),
                jsonPath("$.errors").isEmpty(),
                jsonPath("$.code").value(MALFORMED_JWT.getCode())
            );
      }
    }

    @Nested
    class 요청_헤더에_잘못된_signature_토큰이_주어지면 {

      @Test
      void CustomSignatureException_예외가_발생하고_401_에러응답_객체를_반환한다() throws Exception {
        when(reissueTokenUsecace.reissueToken(TOKEN))
            .thenThrow(new CustomSignatureException("잘못된 토큰입니다"));

        mockMvc.perform(
                get("/api/auth/reissue")
                    .header(AUTHORIZATION_HEADER, BEARER_PREFIX + TOKEN))
            .andExpectAll(
                status().isUnauthorized(),
                result -> assertThat(result.getResolvedException())
                    .isInstanceOf(CustomSignatureException.class),
                jsonPath("$.message").value(INVALID_SIGNATURE_JWT.getMessage()),
                jsonPath("$.status").value(INVALID_SIGNATURE_JWT.getStatus()),
                jsonPath("$.errors").isEmpty(),
                jsonPath("$.code").value(INVALID_SIGNATURE_JWT.getCode())
            );
      }
    }

    @Nested
    class 요청_헤더가_비어있거나_잘못된_값이_주어지면 {

      @Test
      void CustomIllegalArgumentException_예외가_발생하고_401_에러응답_객체를_반환한다() throws Exception {
        when(reissueTokenUsecace.reissueToken(TOKEN))
            .thenThrow(new CustomIllegalArgumentException("잘못된 값 입니다"));

        mockMvc.perform(
                get("/api/auth/reissue")
                    .header(AUTHORIZATION_HEADER, BEARER_PREFIX + TOKEN))
            .andExpectAll(
                status().isUnauthorized(),
                result -> assertThat(result.getResolvedException())
                    .isInstanceOf(CustomIllegalArgumentException.class),
                jsonPath("$.message").value(ILLEGAL_ARGUMENT_JWT.getMessage()),
                jsonPath("$.status").value(ILLEGAL_ARGUMENT_JWT.getStatus()),
                jsonPath("$.errors").isEmpty(),
                jsonPath("$.code").value(ILLEGAL_ARGUMENT_JWT.getCode())
            );
      }
    }

    @Nested
    class 요청_헤더의_토큰에_해당하는_정보가_존재하지_않으면 {

      @Test
      void TeacherNotFoundException_예외가_발생하고_404_에러응답_객체를_반환한다() throws Exception {
        when(reissueTokenUsecace.reissueToken(TOKEN))
            .thenThrow(new TeacherNotFoundException());

        mockMvc.perform(
                get("/api/auth/reissue")
                    .header(AUTHORIZATION_HEADER, BEARER_PREFIX + TOKEN))
            .andExpectAll(
                status().isNotFound(),
                result -> assertThat(result.getResolvedException())
                    .isInstanceOf(TeacherNotFoundException.class),
                jsonPath("$.message").value(TEACHER_NOT_FOUND.getMessage()),
                jsonPath("$.status").value(TEACHER_NOT_FOUND.getStatus()),
                jsonPath("$.errors").isEmpty(),
                jsonPath("$.code").value(TEACHER_NOT_FOUND.getCode())
            );
      }
    }
  }
}