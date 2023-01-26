package com.noti.noti.config.security.jwt.handler;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.ErrorResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType(APPLICATION_JSON);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setCharacterEncoding("utf-8");

    objectMapper.writeValue(response.getOutputStream(),
        ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED));
  }
}
