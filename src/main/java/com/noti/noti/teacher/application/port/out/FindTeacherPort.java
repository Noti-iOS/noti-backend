package com.noti.noti.teacher.application.port.out;

import com.noti.noti.teacher.domain.SocialType;
import com.noti.noti.teacher.domain.Teacher;

public interface FindTeacherPort {

  /* socialId로 teacher 객체 찾기 */
  Teacher findBySocialId(Long social);

  Teacher findBySocialTypeAndSocialId(SocialType socialType, Long socialId);

}
