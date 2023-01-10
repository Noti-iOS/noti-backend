package com.noti.noti.config.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.teacher.domain.SocialType;
import java.io.IOException;
import java.util.Arrays;
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


@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final OAuthManager oAuthManager;

  private final String PREFIX_URL = "/api/teacher/login/";


  public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      OAuthManager oAuthManager) {
    super(authenticationManager);
    this.jwtTokenProvider = jwtTokenProvider;
    this.oAuthManager = oAuthManager;
  }

  /* /login 으로 요청이 올 때 */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    String accessToken = request.getHeader("access_token");
    SocialType socialType = extractSocialType(request);
    String socialCode;

    // 값이 카카오면 kakaoTeacherAdapter
    // 값이 애플이면 AppleTeacherAdapter
    String socialId = oAuthManager.getSocialId(socialType, accessToken); // 각 소셜로그인의 socialId 값

    // 고유번호 저장
    socialCode=socialType.getCode();

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            socialCode+socialId, ""); //


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

  /* 소셜로그인 구분 */
  private SocialType extractSocialType(HttpServletRequest request) {
    return Arrays.stream(SocialType.values())
        .filter(socialType -> socialType.getSocialName()
            .equals(request.getRequestURI().substring(PREFIX_URL.length())))
        .findFirst()
        .orElseThrow(()-> new IllegalArgumentException("잘못된 url 주소입니다."));
  }
}
