package com.noti.noti.config.security.jwt.filter;

import static com.noti.noti.error.ErrorCode.INVALID_REQUEST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.error.exception.InvalidRequestException;
import com.noti.noti.error.exception.OauthAuthenticationException;
import com.noti.noti.teacher.adpater.in.web.dto.OAuthInfo;
import com.noti.noti.teacher.domain.SocialType;
import com.noti.noti.teacher.domain.Teacher;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final OAuthManager oAuthManager;

  private final ObjectMapper objectMapper;

  private final String PREFIX_URL = "/api/teacher/login/";


  public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      OAuthManager oAuthManager,  ObjectMapper objectMapper) {
    super(authenticationManager);
    this.jwtTokenProvider = jwtTokenProvider;
    this.oAuthManager = oAuthManager;
    this.objectMapper = objectMapper;
  }

  /* /login 으로 요청이 올 때 */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    String accessToken = request.getHeader("access-token");

    log.info("access token: {}", accessToken);
    SocialType socialType = extractSocialType(request);

    // 값이 카카오면 kakaoTeacherAdapter
    // 값이 애플이면 AppleTeacherAdapter
    OAuthInfo oauthInfo = null; ;// 각 소셜로그인의 socialId 값

    Teacher teacher = null;
    try {
      oauthInfo = oAuthManager.getOAuthInfo(socialType, accessToken);
      //socialId = oAuthManager.getSocialId(socialType, accessToken);
      teacher = Teacher.builder().id(Long.parseLong(oauthInfo.getSocialId()))
          .profile(oauthInfo.getThumbnailImageUrl()).nickname(
              oauthInfo.getNickname()).build();
    } catch (OauthAuthenticationException e) {
      log.error(e.getMessage());
      request.setAttribute("exception", e.getErrorCode());
      throw e;
    }
    // 고유번호 저장
    String socialCode = socialType.getCode();

    request.setAttribute("teacher", teacher);


    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            socialCode + oauthInfo.getSocialId(), "");

    return getAuthenticationManager().authenticate(authenticationToken);
  }

  /* 로그인 성공 시 호출 */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {

    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    String authorities = authResult.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

    Map<String, String> body = new LinkedHashMap<>();
    body.put("accessToken", jwtTokenProvider.createAccessToken(authResult.getName(), authorities));
    body.put("refreshToken", jwtTokenProvider.createRefreshToken(authResult.getName(), authorities));

    new ObjectMapper().writeValue(response.getOutputStream(), body);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {

    log.info("Authentication failed");

    Teacher teacher = (Teacher)request.getAttribute("teacher");

    System.out.println(teacher.getId());
    System.out.println(teacher.getNickname());
    System.out.println(teacher.getProfile());

    response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    Map<String, LocalDateTime> body = new LinkedHashMap<>();
    body.put("time", LocalDateTime.now());
    objectMapper.writeValue(response.getOutputStream(), body);

  }

  /* 소셜로그인 구분 */
  private SocialType extractSocialType(HttpServletRequest request) {
    return Arrays.stream(SocialType.values())
        .filter(socialType -> socialType.getSocialName()
            .equals(request.getRequestURI().substring(PREFIX_URL.length())))
        .findFirst()
        .orElseThrow(() ->{
          log.info("URI : {}", request.getServletPath());
          request.setAttribute("exception", INVALID_REQUEST);
          throw new InvalidRequestException("잘못된 url 주소입니다");
        });
  }
}
