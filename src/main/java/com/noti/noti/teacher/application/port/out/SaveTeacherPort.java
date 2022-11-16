package com.noti.noti.teacher.application.port.out;

import com.noti.noti.teacher.adpater.out.persistence.TeacherJpaEntity;
import com.noti.noti.teacher.domain.Teacher;

public interface SaveTeacherPort {

  Teacher saveTeacher(Teacher teacher);

}
