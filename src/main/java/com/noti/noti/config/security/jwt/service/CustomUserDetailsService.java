package com.noti.noti.config.security.jwt.service;

import com.noti.noti.teacher.adpater.out.persistence.TeacherPersistenceAdapter;
import com.noti.noti.teacher.domain.Role;
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

  private final TeacherPersistenceAdapter teacherPersistenceAdapter;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserDetails userDetails;

    String[] s = username.split("_"); //String[0]-id, String[1]-social

    String id = s[0];
    SocialType socialType = Arrays.stream(SocialType.values())
        .filter(type -> type.getSocialName().equals(s[1]))
        .findFirst()
        .orElseThrow(()->new IllegalArgumentException("잘못된 요청입니다."));

    boolean validate = teacherPersistenceAdapter.validate(id, socialType);

    if (validate) {// id값 저장되어 있으면 -> 로그인
      Teacher teacher = teacherPersistenceAdapter.findBySocialTypeAndSocialId(socialType, Long.parseLong(id));
      userDetails = createUserDetails(teacher);
    } else {// id 값 저장 안되어 있으면 -> 회원가입 -> 로그인
      Teacher teacher = signIn(id, socialType);
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
  private Teacher signIn(String id, SocialType socialType) {
    Teacher teacher = Teacher.builder()
        .social(Long.parseLong(id))
        .role(Role.ROLE_TEACHER)
        .socialType(socialType)
        .build();

    return teacherPersistenceAdapter.saveTeacher(teacher);

  }
}
