package com.noti.noti.config.security.jwt.filter;

import static com.noti.noti.config.security.jwt.JwtType.REFRESH_TOKEN;
import static com.noti.noti.error.ErrorCode.INVALID_REQUEST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.auth.application.port.out.SaveRefreshTokenPort;
import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.auth.domain.RefreshToken;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.error.exception.InvalidRequestException;
import com.noti.noti.error.exception.OauthAuthenticationException;
import com.noti.noti.teacher.adpater.in.web.dto.OAuthInfo;
import com.noti.noti.teacher.application.port.out.SaveTeacherPort;
import com.noti.noti.teacher.domain.SocialType;
import java.io.IOException;
import java.util.Arrays;
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
  private final SaveTeacherPort saveTeacherPort;
  private final SaveRefreshTokenPort saveRefreshTokenPort;
  private final String PREFIX_URL = "/api/teacher/login/";

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      OAuthManager oAuthManager, ObjectMapper objectMapper, SaveTeacherPort saveTeacherPort,
      SaveRefreshTokenPort saveRefreshTokenPort) {
    super(authenticationManager);
    this.jwtTokenProvider = jwtTokenProvider;
    this.oAuthManager = oAuthManager;
    this.saveTeacherPort = saveTeacherPort;
    this.saveRefreshTokenPort = saveRefreshTokenPort;
    this.objectMapper = objectMapper;
  }

  /* /login 으로 요청이 올 때 */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    String accessToken = request.getHeader("access-token");

    SocialType socialType = extractSocialType(request);

    OAuthInfo oAuthInfo = null;
    String nickname = null;

    try {
      oAuthInfo = oAuthManager.getOAuthInfo(socialType, accessToken);
      Map<String, String> map = objectMapper.readValue(request.getInputStream(), Map.class);
      nickname = map.get("nickname");
    } catch (OauthAuthenticationException e) {
      request.setAttribute("exception", e.getErrorCode());
      throw e;
    } catch (Exception e) {
      log.info("exception: {}", e.getMessage());
    }

    oAuthInfo.changeNickname(nickname);

    request.setAttribute("oAuthInfo", oAuthInfo);

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            String.join(",", socialType.name(), oAuthInfo.getSocialId()),
            "");

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

    JwtToken jwtToken = generateToken(authResult.getName(), authorities);

    saveRefreshTokenPort.saveRefreshToken(
        RefreshToken.builder()
            .refreshToken(jwtToken.getRefreshToken())
            .id(Long.valueOf(authResult.getName()))
            .role(authorities)
            .expiration(REFRESH_TOKEN.getExpiration())
            .build()
    );

    new ObjectMapper().writeValue(response.getOutputStream(), jwtToken);
  }

  // 로그인 요청 정보에 해당하는 회원이 존재하지 않으면 회원가입을 한다. 따로 controller로 분리를 시켜야되나??
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {

    request.getRequestDispatcher("/api/signup").forward(request, response);
  }

  /* 소셜로그인 구분 */
  private SocialType extractSocialType(HttpServletRequest request) {
    return Arrays.stream(SocialType.values())
        .filter(socialType -> socialType.getSocialName()
            .equals(request.getRequestURI().substring(PREFIX_URL.length())))
        .findFirst()
        .orElseThrow(() -> {
          request.setAttribute("exception", INVALID_REQUEST);
          throw new InvalidRequestException("잘못된 url 주소입니다");
        });
  }

  private JwtToken generateToken(String subject, String role) {
    return new JwtToken(jwtTokenProvider.createAccessToken(subject, role),
        jwtTokenProvider.createRefreshToken(subject, role));
  }
}
