package com.noti.noti.teacher.application.port.out;

import com.noti.noti.teacher.domain.Teacher;

public interface FindTeacherPort {

  /* socialId로 teacher 객체 찾기 */
  Teacher findBySocialId(Long social);

}
