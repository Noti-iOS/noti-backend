package com.noti.noti.config.security.jwt.filter;

import static com.noti.noti.error.ErrorCode.EXPIRED_JWT;
import static com.noti.noti.error.ErrorCode.ILLEGAL_ARGUMENT_JWT;
import static com.noti.noti.error.ErrorCode.INVALID_SIGNATURE_JWT;
import static com.noti.noti.error.ErrorCode.MALFORMED_JWT;
import static com.noti.noti.error.ErrorCode.UNSUPPORTED_JWT;

import com.noti.noti.config.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";
  private final JwtTokenProvider jwtTokenProvider;

  /* 토큰 인증 정보를 SecurityContext에 저장 */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String jwt = resolveToken(request);

    try {
      if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
        Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
      //jwtTokenProvider.validateToken(jwt);
    } catch (ExpiredJwtException e) {
      log.error(e.getMessage());
      request.setAttribute("exception", EXPIRED_JWT);
    } catch (UnsupportedJwtException e) {
      log.error(e.getMessage());
      request.setAttribute("exception", UNSUPPORTED_JWT);
    } catch (MalformedJwtException e) {
      log.error(e.getMessage());
      request.setAttribute("exception", MALFORMED_JWT);
    } catch (SignatureException e) {
      log.error(e.getMessage());
      request.setAttribute("exception", INVALID_SIGNATURE_JWT);
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage());
      request.setAttribute("exception", ILLEGAL_ARGUMENT_JWT);
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
    return new AntPathMatcher().match("/api/teacher/login/**", request.getServletPath());
  }
}
