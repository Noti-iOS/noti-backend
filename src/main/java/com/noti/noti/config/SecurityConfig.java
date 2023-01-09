package com.noti.noti.config;

import com.noti.noti.config.security.jwt.JwtTokenProvider;
import com.noti.noti.teacher.adpater.out.persistence.TeacherPersistenceAdapter;
import com.noti.noti.teacher.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final TeacherPersistenceAdapter teacherPersistenceAdapter;
  private final JwtTokenProvider jwtTokenProvider;
  private final AccessDeniedHandler accessDeniedHandler;
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final JwtConfig jwtConfig;

  @Bean // HttpSecurity
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf().disable()
        .cors()
        .and()
        .httpBasic().disable()

        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .accessDeniedHandler(accessDeniedHandler)

        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .authorizeRequests()
        .antMatchers("/api/teacher/login/**","/", "/swagger-ui.html","/swagger-ui/**", "/api-docs/**").permitAll()
        .antMatchers("api/teacher/home").hasRole("TEACHER")
        .anyRequest().authenticated()

        .and()
        .apply(jwtConfig);

    return http.build();
  }


  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }


  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
