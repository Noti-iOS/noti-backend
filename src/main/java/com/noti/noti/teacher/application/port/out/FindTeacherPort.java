package com.noti.noti.teacher.application.port.out;

import com.noti.noti.teacher.domain.SocialType;
import com.noti.noti.teacher.domain.Teacher;
import java.util.Optional;

public interface FindTeacherPort {

  /* socialId로 teacher 객체 찾기 */
  Teacher findBySocialId(Long social);

  Optional<Teacher> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

  Optional<Teacher> findById(Long Id);

}
