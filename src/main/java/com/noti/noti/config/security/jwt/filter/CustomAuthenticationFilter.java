package com.noti.noti.config.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.teacher.adpater.in.web.dto.KakaoDto.TeacherInfo;
import com.noti.noti.teacher.adpater.out.persistence.TeacherPersistenceAdapter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final TeacherPersistenceAdapter teacherPersistenceAdapter;
  private final JwtTokenProvider jwtTokenProvider;


  public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
      TeacherPersistenceAdapter teacherPersistenceAdapter,
      JwtTokenProvider jwtTokenProvider) {
    super(authenticationManager);
    this.jwtTokenProvider = jwtTokenProvider;
    this.teacherPersistenceAdapter = teacherPersistenceAdapter;
  }

  /* /login 으로 요청이 올 때 */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    String accessToken = request.getHeader("access_token"); // 1

    TeacherInfo teacherInfo = WebClient.create( // 2
            "https://kapi.kakao.com/v2/user/me")
        .get()
        .header("Authorization", "Bearer " + accessToken)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(RuntimeException::new))
        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(RuntimeException::new))
        .bodyToMono(TeacherInfo.class)
        .block();

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(teacherInfo.getId().toString(), "");

    return getAuthenticationManager().authenticate(authenticationToken);
  }

  /* 로그인 성공 시 호출 */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {

    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    response.setHeader("access_token", jwtTokenProvider.createAccessToken(authResult));
    response.setHeader("refresh_token", jwtTokenProvider.createRefreshToken(authResult));

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("bool", true);
    new ObjectMapper().writeValue(response.getOutputStream(), body);
  }

  /* 로그인 실패 시 호출 */
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    System.out.println(failed.toString());
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("bool", false);

    new ObjectMapper().writeValue(response.getOutputStream(), body);
  }
}
