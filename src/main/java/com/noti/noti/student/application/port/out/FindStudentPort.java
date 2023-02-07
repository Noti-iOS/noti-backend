package com.noti.noti.student.application.port.out;

import com.noti.noti.student.domain.model.Student;
import java.util.Optional;

public interface FindStudentPort {
  Optional<Student> findStudentById(Long id);
}
