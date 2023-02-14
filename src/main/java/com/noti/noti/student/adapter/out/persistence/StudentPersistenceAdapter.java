package com.noti.noti.student.adapter.out.persistence;

import com.noti.noti.student.adapter.out.persistence.jpa.StudentJpaRepository;
import com.noti.noti.student.application.port.out.FindStudentPort;
import com.noti.noti.student.domain.model.Student;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentPersistenceAdapter implements FindStudentPort {

  private final StudentJpaRepository studentJpaRepository;
  private final StudentMapper studentMapper;

  @Override
  public Optional<Student> findStudentById(Long id) {
    return studentJpaRepository.findById(id).map(studentMapper::mapToDomainEntity);
  }
}
