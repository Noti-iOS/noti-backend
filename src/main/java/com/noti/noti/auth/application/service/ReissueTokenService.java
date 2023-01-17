package com.noti.noti.auth.application.service;

import static com.noti.noti.error.ErrorCode.EXPIRED_JWT;
import static com.noti.noti.error.ErrorCode.ILLEGAL_ARGUMENT_JWT;
import static com.noti.noti.error.ErrorCode.INVALID_SIGNATURE_JWT;
import static com.noti.noti.error.ErrorCode.MALFORMED_JWT;
import static com.noti.noti.error.ErrorCode.UNSUPPORTED_JWT;

import com.noti.noti.auth.application.port.in.JwtToken;
import com.noti.noti.auth.application.port.in.ReissueTokenUsecace;
import com.noti.noti.common.application.port.out.JwtPort;
import com.noti.noti.error.exception.BusinessException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;

import com.noti.noti.teacher.domain.Teacher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReissueTokenService implements ReissueTokenUsecace {

  private final JwtPort jwtPort;
  private final FindTeacherPort findTeacherPort;

  @Override
  public JwtToken reissueToken(String token) {
    try {
      jwtPort.validateToken(token);
      Long teacherId = Long.parseLong(jwtPort.getSubject(token));
      Teacher teacher = findTeacherPort.findById(teacherId);
      String accessToken = jwtPort.createAccessToken(teacher.getId().toString(),
          teacher.getRole().name());
      String refreshToken = jwtPort.createRefreshToken(teacher.getId().toString(),
          teacher.getRole().name());

      return new JwtToken(accessToken, refreshToken);
    } catch (BusinessException e) {
      throw e;
    }
  }
}
