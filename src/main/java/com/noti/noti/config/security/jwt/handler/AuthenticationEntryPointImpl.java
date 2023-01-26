package com.noti.noti.config.security.jwt.handler;

import static com.noti.noti.error.ErrorCode.AUTHENTICATION_FAILED;
import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.ErrorResponse;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/* 토큰 인증 실패, 인증 헤더 정상적으로 받지 못했을 때 핸들링 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    ErrorCode errorCode = (ErrorCode) request.getAttribute("exception");

    setResponse(response, errorCode);
  }

  private void setResponse(HttpServletResponse response, ErrorCode code) throws IOException {
    response.setContentType(APPLICATION_JSON);
    response.setCharacterEncoding("utf-8");

    if (Objects.isNull(code)) {
      log.error(AUTHENTICATION_FAILED.getCode());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      objectMapper.writeValue(response.getOutputStream(), ErrorResponse.of(AUTHENTICATION_FAILED));
    } else {
      log.error(code.getCode());
      response.setStatus(HttpStatus.valueOf(code.getStatus()).value());
      objectMapper.writeValue(response.getOutputStream(), ErrorResponse.of(code));
    }
  }
}
