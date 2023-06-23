package com.noti.noti.studenthomework.application.port.out;

import com.noti.noti.studenthomework.domain.model.StudentHomework;
import java.util.List;

public interface SaveStudentHomeworkPort {

  void saveAllStudentHomeworks(List<StudentHomework> studentHomeworks);
}
