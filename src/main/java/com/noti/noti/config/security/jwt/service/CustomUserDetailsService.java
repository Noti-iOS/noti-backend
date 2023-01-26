package com.noti.noti.config.security.jwt.service;

import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.domain.SocialType;
import com.noti.noti.teacher.domain.Teacher;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final FindTeacherPort findTeacherPort;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // username = id = socialType.code + socialId
    String socialCode = username.substring(0, 3);
    String socialId = username.substring(3);

    SocialType socialType = Arrays.stream(SocialType.values())
        .filter(type -> type.getCode().equals(socialCode))
        .findFirst()
        .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 없습니다"));

    Teacher teacher = findTeacherPort.findBySocialTypeAndSocialId(socialType, socialId)
        .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 없습니다"));

    return createUserDetails(teacher);
  }

  private UserDetails createUserDetails(Teacher teacher) {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(teacher.getRole().name());
    UserDetails userDetails = new User(teacher.getId().toString(),
        new BCryptPasswordEncoder().encode(""), Collections.singleton(grantedAuthority));
    return userDetails;
  }
}
