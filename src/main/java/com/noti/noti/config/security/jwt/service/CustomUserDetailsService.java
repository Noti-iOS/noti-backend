package com.noti.noti.config.security.jwt.service;

import com.noti.noti.teacher.adpater.out.persistence.TeacherPersistenceAdapter;
import com.noti.noti.teacher.domain.Role;
import com.noti.noti.teacher.domain.Teacher;
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

  private final TeacherPersistenceAdapter teacherPersistenceAdapter;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails userDetails;

    boolean validate = teacherPersistenceAdapter.validate(username);

    if (validate) {// id값 저장되어 있으면 -> 로그인
      Teacher teacher = teacherPersistenceAdapter.findBySocialId(Long.parseLong(username));
      userDetails = createUserDetails(teacher);
    } else {// id 값 저장 안되어 있으면 -> 회원가입 -> 로그인
      Teacher teacher = signIn(username);
      userDetails = createUserDetails(teacher);
    }
    return userDetails;
  }

  private UserDetails createUserDetails(Teacher teacher) {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(teacher.getRole().name());
    UserDetails userDetails = new User(teacher.getId().toString(),
        bCryptPasswordEncoder.encode(""), Collections.singleton(grantedAuthority));
    return userDetails;
  }

  /* 회원가입 - 해당 아이디 없으면 저장 */
  private Teacher signIn(String username) {
    Teacher teacher = Teacher.builder()
        .social(Long.parseLong(username))
        .role(Role.ROLE_TEACHER)
        .build();

    return teacherPersistenceAdapter.saveTeacher(teacher);

  }
}
