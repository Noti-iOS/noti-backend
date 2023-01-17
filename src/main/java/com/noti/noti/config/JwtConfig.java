package com.noti.noti.config;

import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.config.security.jwt.filter.CustomAuthenticationFilter;
import com.noti.noti.config.security.jwt.filter.CustomJwtFilter;
import com.noti.noti.config.security.jwt.filter.OAuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class JwtConfig extends AbstractHttpConfigurer<JwtConfig, HttpSecurity> {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomJwtFilter customJwtFilter;
  private final OAuthManager oAuthManager;

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
        authenticationManager, jwtTokenProvider, oAuthManager);
    customAuthenticationFilter.setFilterProcessesUrl("/api/teacher/login/**");
    return customAuthenticationFilter;
  }
}
