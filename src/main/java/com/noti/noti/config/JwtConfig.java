package com.noti.noti.config;

import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.config.security.jwt.filter.CustomAuthenticationFilter;
import com.noti.noti.config.security.jwt.filter.JwtFilter;
import com.noti.noti.config.security.jwt.filter.OAuthManager;
import com.noti.noti.teacher.adpater.out.persistence.TeacherPersistenceAdapter;
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
  private final JwtFilter jwtFilter;
  private final TeacherPersistenceAdapter teacherPersistenceAdapter;
  private final OAuthManager oAuthManager;

  @Override
  public void configure(HttpSecurity builder) throws Exception {
    AuthenticationManager authenticationManager = builder.getSharedObject(
        AuthenticationManager.class);
    builder.addFilter(usernamePasswordAuthenticationFilter(authenticationManager))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }

  public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(
      AuthenticationManager authenticationManager) {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
        authenticationManager, teacherPersistenceAdapter, jwtTokenProvider, oAuthManager);
    customAuthenticationFilter.setFilterProcessesUrl("/api/teacher/login/**");
    return customAuthenticationFilter;
  }
}
