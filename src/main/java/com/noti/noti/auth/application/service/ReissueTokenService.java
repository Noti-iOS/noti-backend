package com.noti.noti.auth.application.service;

import com.noti.noti.auth.application.exception.NotFoundRefreshTokenException;
import com.noti.noti.auth.application.port.out.FindRefreshTokenPort;
import com.noti.noti.auth.application.port.out.SaveRefreshTokenPort;
import com.noti.noti.auth.application.port.out.DeleteRefreshTokenPort;
import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.auth.application.port.in.ReissueTokenUsecace;
import com.noti.noti.auth.domain.RefreshToken;
import com.noti.noti.common.application.port.out.JwtPort;
import com.noti.noti.config.security.jwt.JwtType;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;

import com.noti.noti.teacher.domain.Teacher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 임호준
 * @description 토큰갱신 Service
 * @updated Refresh Token 저장소에서 존재유무 판단 후 삭제 저장 로직 추가
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReissueTokenService implements ReissueTokenUsecace {

  private final JwtPort jwtPort;
  private final FindTeacherPort findTeacherPort;
  private final SaveRefreshTokenPort saveRefreshTokenPort;
  private final DeleteRefreshTokenPort deleteRefreshTokenPort;
  private final FindRefreshTokenPort findRefreshTokenPort;

  @Override
  public JwtToken reissueToken(String token) {
    // Redis에서 저장해놓은 Refresh token과 일치하는지 확인
    RefreshToken foundToken = findRefreshTokenPort.findRefreshTokenById(token)
        .orElseThrow(NotFoundRefreshTokenException::new);

    // 기존 토큰 삭제
    deleteRefreshTokenPort.deleteRefreshToken(foundToken);

    // 조회한 토큰 정보로 선생님 조회, Role이 있기 때문에 추후에 Student, Teacher 분기 처리가 가능할 듯 싶습니다.
    Long teacherId = foundToken.getId();
    Teacher foundTeahcer = findTeacherPort.findById(teacherId)
        .orElseThrow(TeacherNotFoundException::new);

    // Jwt 토큰 생성 후 반환. Redis에 새로운 Refresh token 저장
    String accessToken = jwtPort.createAccessToken(foundTeahcer.getId().toString(),
        foundTeahcer.getRole().name());
    String refreshToken = jwtPort.createRefreshToken(foundTeahcer.getId().toString(),
        foundTeahcer.getRole().name());

    saveRefreshTokenPort.saveRefreshToken(
        RefreshToken.builder()
            .refreshToken(refreshToken)
            .id(foundTeahcer.getId())
            .role(foundTeahcer.getRole().name())
            .expiration(JwtType.REFRESH_TOKEN.getExpiration())
            .build()
    );

    return new JwtToken(accessToken, refreshToken);
  }
}
