package com.noti.noti.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.auth.application.port.out.SaveRefreshTokenPort;
import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.config.security.jwt.filter.CustomAuthenticationFilter;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.config.security.jwt.filter.OAuthManager;
import com.noti.noti.teacher.application.port.out.SaveTeacherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class JwtConfig extends AbstractHttpConfigurer<JwtConfig, HttpSecurity> {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomJwtFilter customJwtFilter;
  private final OAuthManager oAuthManager;
  private final SaveTeacherPort saveTeacherPort;
  private final SaveRefreshTokenPort saveRefreshTokenPort;
  private final ObjectMapper objectMapper;

  @Override
  public void configure(HttpSecurity builder) throws Exception {
    AuthenticationManager authenticationManager = builder.getSharedObject(
        AuthenticationManager.class);
    builder.addFilter(usernamePasswordAuthenticationFilter(authenticationManager))
        .addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
  }

  public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(
      AuthenticationManager authenticationManager) {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
        authenticationManager, jwtTokenProvider, oAuthManager, objectMapper, saveTeacherPort,
        saveRefreshTokenPort);
    customAuthenticationFilter.setRequiresAuthenticationRequestMatcher(
        new AntPathRequestMatcher("/api/teacher/login/**",
            "POST"));
    return customAuthenticationFilter;
  }

}
