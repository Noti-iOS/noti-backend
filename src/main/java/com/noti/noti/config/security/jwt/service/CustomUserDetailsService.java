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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // username = id = socialType.code + socialId

    UserDetails userDetails;

    String socialCode = username.substring(0, 3);
    String socialId = username.substring(3);

    SocialType socialType = Arrays.stream(SocialType.values())
        .filter(type -> type.getCode().equals(socialCode))
        .findFirst()
        .orElseThrow(()->new IllegalArgumentException("잘못된 요청입니다."));

    boolean validate = teacherPersistenceAdapter.validate(socialId, socialType);

    System.out.println(validate);
    if (validate) {// id값 저장되어 있으면 -> 로그인
      Teacher teacher = teacherPersistenceAdapter.findById(Long.parseLong(username));
      //Teacher teacher = teacherPersistenceAdapter.findBySocialTypeAndSocialId(socialType, Long.parseLong(socialId));
      userDetails = createUserDetails(teacher);
    } else {// id 값 저장 안되어 있으면 -> 회원가입 -> 로그인
      Teacher teacher = signIn(socialId, socialType);
      userDetails = createUserDetails(teacher);
    }
    return userDetails;
  }

  private UserDetails createUserDetails(Teacher teacher) {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(teacher.getRole().name());
    UserDetails userDetails = new User(teacher.getId().toString(),
        new BCryptPasswordEncoder().encode(""), Collections.singleton(grantedAuthority));
    return userDetails;
  }

  /* 회원가입 - 해당 아이디 없으면 저장 */
  private Teacher signIn(String socialId, SocialType socialType) {
    Teacher teacher = Teacher.builder()
        .id(Long.parseLong(socialType.getCode()+socialId)) // id = socialType.code + socialId
        .social(Long.parseLong(socialId))
        .role(Role.TEACHER)
        .socialType(socialType)
        .build();

    return teacherPersistenceAdapter.saveTeacher(teacher);

  }
}
