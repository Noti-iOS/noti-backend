package com.noti.noti.auth.application.service;

import com.noti.noti.auth.application.port.in.SignUpCommand;
import com.noti.noti.auth.application.port.in.SignUpUsecase;
import com.noti.noti.auth.application.port.out.SaveRefreshTokenPort;
import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.auth.domain.RefreshToken;
import com.noti.noti.common.application.port.out.JwtPort;
import com.noti.noti.config.security.jwt.TokenExpiration;
import com.noti.noti.teacher.application.port.out.SaveTeacherPort;
import com.noti.noti.teacher.domain.Role;
import com.noti.noti.teacher.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 임호준
 * @description 회원가입 유스케이스 서비스
 */
@Service
@RequiredArgsConstructor
public class SignUpService implements SignUpUsecase {

  private final SaveTeacherPort saveTeacherPort;
  private final JwtPort jwtPort;
  private final SaveRefreshTokenPort saveRefreshTokenPort;


  @Override
  public JwtToken apply(SignUpCommand signUpCommand) {
    // Command 정보로 teacher 도메인모델 생성 후 저장
    Teacher teacher = Teacher.builder()
        .profile(signUpCommand.getThumbnailImageUrl())
        .nickname(signUpCommand.getNickname())
        .email(signUpCommand.getEmail())
        .socialId(signUpCommand.getSocialId())
        .socialType(signUpCommand.getSocialType())
        .role(Role.ROLE_TEACHER).build();

    Teacher savedTeacher = saveTeacherPort.saveTeacher(teacher);

    // 저장된 정보를 이용해 Access, Refresh token 생성
    String accessToken = jwtPort.createAccessToken(savedTeacher.getId().toString(),
        savedTeacher.getRole().name());
    String refreshToken = jwtPort.createRefreshToken(savedTeacher.getId().toString(),
        savedTeacher.getRole().name());

    // Refresh token Redis 저장
    saveRefreshTokenPort.saveRefreshToken(
        RefreshToken.builder()
            .refreshToken(refreshToken)
            .id(savedTeacher.getId())
            .role(savedTeacher.getRole().name())
            .expiration(TokenExpiration.REFRESH_TOKEN.getExpiration())
            .build()
    );

    return new JwtToken(accessToken, refreshToken);
  }
}
