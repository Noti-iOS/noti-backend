package com.noti.noti.auth.application.service;

import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.auth.application.port.in.ReissueTokenUsecace;
import com.noti.noti.common.application.port.out.JwtPort;
import com.noti.noti.error.exception.BusinessException;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
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
      Teacher teacher = findTeacherPort.findById(teacherId).orElseThrow(TeacherNotFoundException::new);
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
