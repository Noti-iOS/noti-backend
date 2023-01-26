package com.noti.noti.auth.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.common.application.port.out.JwtPort;
import com.noti.noti.error.exception.CustomExpiredJwtException;
import com.noti.noti.error.exception.CustomIllegalArgumentException;
import com.noti.noti.error.exception.CustomMalformedJwtException;
import com.noti.noti.error.exception.CustomSignatureException;
import com.noti.noti.error.exception.CustomUnsupportedJwtException;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.domain.Role;
import com.noti.noti.teacher.domain.Teacher;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ReissueTokenServiceTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class ReissueTokenServiceTest {

  @InjectMocks
  private ReissueTokenService reissueTokenService;

  @Mock
  private JwtPort jwtPort;

  @Mock
  private FindTeacherPort findTeacherPort;

  private final String TOKEN = "TOKEN";

  @Nested
  class reissueToken_메서드는 {

    @Nested
    class 만료된_토큰이_주어지면 {

      @Test
      void CustomeExpriedJwtException_예외가_발생한다() {
        // void 메소드에서 사용하는 방법.
        doThrow(new CustomExpiredJwtException("만료된 토큰 입니다")).when(jwtPort).validateToken(TOKEN);

        assertAll(
            () -> assertThatThrownBy(() -> reissueTokenService.reissueToken(TOKEN))
                .isInstanceOf(CustomExpiredJwtException.class),
            () -> verify(findTeacherPort, never()).findById(any(Long.class))
        );
      }
    }

    @Nested
    class 지원하지_않는_토큰이_주어지면 {

      @Test
      void CustomUnsupportedJwtException_예외가_발생한다() {
        doThrow(new CustomUnsupportedJwtException("지원하지 않는 토큰입니다")).when(jwtPort)
            .validateToken(TOKEN);

        assertAll(
            () -> assertThatThrownBy(() -> reissueTokenService.reissueToken(TOKEN))
                .isInstanceOf(CustomUnsupportedJwtException.class),
            () -> verify(findTeacherPort, never()).findById(any(Long.class))
        );
      }
    }

    @Nested
    class 잘못된_토큰이_주어지면 {

      @Test
      void CustomMalformedJwtException_예외가_발생한다() {
        doThrow(new CustomMalformedJwtException("만료된 토큰 입니다")).when(jwtPort).validateToken(TOKEN);

        assertAll(
            () -> assertThatThrownBy(() -> reissueTokenService.reissueToken(TOKEN))
                .isInstanceOf(CustomMalformedJwtException.class),
            () -> verify(findTeacherPort, never()).findById(any(Long.class))
        );
      }
    }

    @Nested
    class 잘못된_signature_토큰이_주어지면 {

      @Test
      void CustomSignatureException_예외가_발생한다() {
        doThrow(new CustomSignatureException("잘못된 토큰 입니다")).when(jwtPort).validateToken(TOKEN);

        assertAll(
            () -> assertThatThrownBy(() -> reissueTokenService.reissueToken(TOKEN))
                .isInstanceOf(CustomSignatureException.class),
            () -> verify(findTeacherPort, never()).findById(any(Long.class))
        );
      }
    }

    @Nested
    class 인자가_잘못된_값이_주어지면 {

      @Test
      void CustomIllegalArgumentException_예외가_발생한다() {
        doThrow(new CustomIllegalArgumentException("잘못된 토큰 입니다")).when(jwtPort)
            .validateToken(TOKEN);

        assertAll(
            () -> assertThatThrownBy(() -> reissueTokenService.reissueToken(TOKEN))
                .isInstanceOf(CustomIllegalArgumentException.class),
            () -> verify(findTeacherPort, never()).findById(any(Long.class))
        );
      }
    }

    @Nested
    class 토큰에_해당하는_정보가_존재하지_않으면 {

      @Test
      void TeacherNotFoundException_예외가_발생한다() {
        doNothing().when(jwtPort).validateToken(TOKEN);
        when(jwtPort.getSubject(TOKEN)).thenReturn("123");
        when(findTeacherPort.findById(Long.parseLong("123"))).thenReturn(Optional.empty());
        assertAll(
            () -> assertThatThrownBy(() -> reissueTokenService.reissueToken(TOKEN))
                .isInstanceOf(TeacherNotFoundException.class),
            () -> verify(jwtPort, never()).createAccessToken(anyString(), anyString()),
            () -> verify(jwtPort, never()).createRefreshToken(anyString(), anyString())
        );
      }
    }

    @Nested
    class 올바른_토큰이_주어지면 {

      final Teacher TEACHER = Teacher.builder().id(123L).role(Role.ROLE_TEACHER).build();
      final String ACCESS_TOKEN = "access_token";
      final String REFRESH_TOKEN = "refresh_token";

      @Test
      void 재갱신된_토큰_객체를_반환한다() {
        doNothing().when(jwtPort).validateToken(TOKEN);
        when(jwtPort.getSubject(TOKEN)).thenReturn("123");
        when(findTeacherPort.findById(123L)).thenReturn(Optional.of(TEACHER));
        when(jwtPort.createAccessToken(TEACHER.getId().toString(), TEACHER.getRole().name()))
            .thenReturn(ACCESS_TOKEN);
        when(jwtPort.createRefreshToken(TEACHER.getId().toString(), TEACHER.getRole().name()))
            .thenReturn(REFRESH_TOKEN);

        JwtToken jwtToken = reissueTokenService.reissueToken(TOKEN);
        assertAll(
            () -> assertThat(jwtToken.getAccessToken()).isEqualTo(ACCESS_TOKEN),
            () -> assertThat(jwtToken.getRefreshToken()).isEqualTo(REFRESH_TOKEN)
        );
      }
    }
  }
}