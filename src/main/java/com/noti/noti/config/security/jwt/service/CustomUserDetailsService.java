package com.noti.noti.config.security.jwt.service;

import com.noti.noti.teacher.adpater.out.persistence.TeacherJpaEntity;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import com.noti.noti.teacher.adpater.out.persistence.TeacherPersistenceAdapter;
import com.noti.noti.teacher.adpater.out.persistence.TeacherRepository;
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
public class CustomUserDetailsService implements UserDetailsService/*Adapter*/ {

  private final TeacherRepository teacherRepository;
  private final TeacherPersistenceAdapter teacherPersistenceAdapter;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    TeacherJpaEntity teacherJpaEntity = teacherRepository.findBySocialId(Long.parseLong(username))
        .orElse(save(username));
    UserDetails userDetails = createUserDetails(teacherJpaEntity);
    return userDetails;
  }

  private UserDetails createUserDetails(TeacherJpaEntity teacher) {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(teacher.getRole().name());
    UserDetails userDetails = new User(teacher.getUsername().toString(),
        bCryptPasswordEncoder.encode(""), Collections.singleton(grantedAuthority));
    return userDetails;
  }

  private TeacherJpaEntity save(String username){
    Teacher teacher = Teacher.builder().social(Long.parseLong(username)).build();
    return teacherPersistenceAdapter.saveTeacher(teacher);
  }
}
