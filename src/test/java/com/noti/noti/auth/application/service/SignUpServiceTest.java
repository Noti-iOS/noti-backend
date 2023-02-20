package com.noti.noti.auth.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.noti.noti.auth.application.port.in.SignUpCommand;
import com.noti.noti.auth.application.port.out.SaveRefreshTokenPort;
import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.auth.domain.RefreshToken;
import com.noti.noti.common.application.port.out.JwtPort;
import com.noti.noti.config.security.jwt.JwtType;
import com.noti.noti.teacher.application.port.out.SaveTeacherPort;
import com.noti.noti.teacher.domain.Role;
import com.noti.noti.teacher.domain.SocialType;
import com.noti.noti.teacher.domain.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("SignUpServiceTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {

  @InjectMocks
  SignUpService signUpService;

  @Mock
  SaveTeacherPort saveTeacherPort;
  @Mock
  JwtPort jwtPort;
  @Mock
  SaveRefreshTokenPort saveRefreshTokenPort;

  final String ACCESS_TOKEN = "ACCESS_TOKEN";
  final String REFRESH_TOKEN = "REFRESH_TOKEN";
  final Long TEACHER_ID = 1L;

  @Nested
  class apply_메서드는 {

    @Nested
    class Teacher_객체가_주어지면 {

      @Test
      void 성공적으로_회원을_저장하고_저장된_회원의_jwt_token을_반환한다() {
        when(saveTeacherPort.saveTeacher(any(Teacher.class))).thenReturn(createTeacher(TEACHER_ID));
        when(jwtPort.createAccessToken(anyString(), anyString())).thenReturn(ACCESS_TOKEN);
        when(jwtPort.createRefreshToken(anyString(), anyString())).thenReturn(REFRESH_TOKEN);
        when(saveRefreshTokenPort.saveRefreshToken(any(RefreshToken.class)))
            .thenReturn(createRefreshToken());

        JwtToken jwtToken = signUpService.apply(SignUpCommand.builder()
            .email("email")
            .nickname("noti")
            .socialId("social")
            .socialType(SocialType.KAKAO)
            .thumbnailImageUrl("image")
            .build());

        assertAll(
          () -> assertThat(jwtToken.getAccessToken()).isEqualTo(ACCESS_TOKEN),
          () -> assertThat(jwtToken.getRefreshToken()).isEqualTo(REFRESH_TOKEN)
        );
      }
    }
  }

  RefreshToken createRefreshToken(){
    return RefreshToken.builder()
        .expiration(JwtType.REFRESH_TOKEN.getExpiration())
        .id(TEACHER_ID)
        .refreshToken(REFRESH_TOKEN)
        .role(Role.ROLE_TEACHER.name())
        .build();
  }
  Teacher createTeacher(Long id) {
    return Teacher.builder()
        .id(id)
        .role(Role.ROLE_TEACHER)
        .build();
  }
}