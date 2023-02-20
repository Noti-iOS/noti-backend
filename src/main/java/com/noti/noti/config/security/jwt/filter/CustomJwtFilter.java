package com.noti.noti.config.security.jwt.filter;

import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.config.security.jwt.JwtType;
import com.noti.noti.error.exception.BusinessException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomJwtFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";
  private final JwtTokenProvider jwtTokenProvider;
  private final AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Value("${excludeUrls}")
  private final List<String> excludeUrls;

  /* 토큰 인증 정보를 SecurityContext에 저장 */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String jwt = resolveToken(request);

    try {
      // 토큰 검증
      jwtTokenProvider.validateToken(jwt);

      //토큰 타입이 Access Token 일 경우에만 인증토큰객체 저장
      if (jwtTokenProvider.getTokenType(jwt).equals(JwtType.ACCESS_TOKEN.name())) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (BusinessException e) {
      request.setAttribute("exception", e.getErrorCode());
    } catch (AuthenticationException e) {
    }

    filterChain.doFilter(request, response);
  }

  /* 요청헤더에서 토큰 추출 */
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return excludeUrls.stream()
        .anyMatch(url -> antPathMatcher.match(url, request.getServletPath()));
  }
}
