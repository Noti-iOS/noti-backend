package com.noti.noti.auth.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.auth.application.exception.NotFoundRefreshTokenException;
import com.noti.noti.auth.application.port.out.DeleteRefreshTokenPort;
import com.noti.noti.auth.application.port.out.FindRefreshTokenPort;
import com.noti.noti.auth.application.port.out.SaveRefreshTokenPort;
import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.auth.domain.RefreshToken;
import com.noti.noti.common.application.port.out.JwtPort;
import com.noti.noti.config.security.jwt.JwtType;
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
  JwtPort jwtPort;

  @Mock
  SaveRefreshTokenPort saveRefreshTokenPort;

  @Mock
  DeleteRefreshTokenPort deleteRefreshTokenPort;

  @Mock
  FindRefreshTokenPort findRefreshTokenPort;

  @Mock
  FindTeacherPort findTeacherPort;

  final String TOKEN = "TOKEN";
  final Teacher TEACHER = Teacher.builder().id(123L).role(Role.ROLE_TEACHER).build();
  final String ACCESS_TOKEN = "access_token";
  final String REFRESH_TOKEN = "refresh_token";

  @Nested
  class reissueToken_메서드는 {
    @Nested
    class 토큰에_해당하는_선생님_정보가_존재하지_않으면 {
      @Test
      void TeacherNotFoundException_예외가_발생한다() {
        when(findRefreshTokenPort.findRefreshTokenById(anyString()))
            .thenReturn(Optional.of(createRefreshToken()));
        doNothing().when(deleteRefreshTokenPort).deleteRefreshToken(any(RefreshToken.class));
        when(findTeacherPort.findById(anyLong())).thenReturn(Optional.empty());
        assertAll(
            () -> assertThatThrownBy(() -> reissueTokenService.reissueToken(TOKEN))
                .isInstanceOf(TeacherNotFoundException.class),
            () -> verify(jwtPort, never()).createAccessToken(anyString(), anyString()),
            () -> verify(jwtPort, never()).createAccessToken(anyString(), anyString()),
            () -> verify(saveRefreshTokenPort, never()).saveRefreshToken(any(RefreshToken.class))
        );
      }
    }

    @Nested
    class 토큰에_해당하는_정보가_존재하지_않으면 {
      @Test
      void NotFoundRefreshTokenException_예외가_발생한다() {
        when(findRefreshTokenPort.findRefreshTokenById(anyString()))
            .thenReturn(Optional.empty());

        assertAll(
            () -> assertThatThrownBy(() -> reissueTokenService.reissueToken(TOKEN))
                .isInstanceOf(NotFoundRefreshTokenException.class),
            () -> verify(jwtPort, never()).createAccessToken(anyString(), anyString()),
            () -> verify(jwtPort, never()).createAccessToken(anyString(), anyString()),
            () -> verify(saveRefreshTokenPort, never()).saveRefreshToken(any(RefreshToken.class)),
            () -> verify(deleteRefreshTokenPort, never()).deleteRefreshToken(any(RefreshToken.class))
        );
      }
    }

    @Nested
    class 올바른_토큰이_주어지면 {
      @Test
      void 재갱신된_토큰_객체를_저장하고_반환한다() {
        when(findRefreshTokenPort.findRefreshTokenById(anyString()))
            .thenReturn(Optional.of(createRefreshToken()));
        doNothing().when(deleteRefreshTokenPort).deleteRefreshToken(any(RefreshToken.class));
        when(findTeacherPort.findById(anyLong())).thenReturn(Optional.of(TEACHER));
        when(jwtPort.createAccessToken(TEACHER.getId().toString(), TEACHER.getRole().name()))
            .thenReturn(ACCESS_TOKEN);
        when(jwtPort.createRefreshToken(TEACHER.getId().toString(), TEACHER.getRole().name()))
            .thenReturn(REFRESH_TOKEN);
        when(saveRefreshTokenPort.saveRefreshToken(any(RefreshToken.class)))
            .thenReturn(createRefreshToken());

        JwtToken jwtToken = reissueTokenService.reissueToken(TOKEN);

        assertAll(
            () -> assertThat(jwtToken.getAccessToken()).isEqualTo(ACCESS_TOKEN),
            () -> assertThat(jwtToken.getRefreshToken()).isEqualTo(REFRESH_TOKEN)
        );
      }
    }
  }

  RefreshToken createRefreshToken() {
    return RefreshToken.builder()
        .refreshToken(REFRESH_TOKEN)
        .id(1L)
        .role("ROLE_TEACHER")
        .expiration(JwtType.REFRESH_TOKEN.getExpiration())
        .build();
  }
}