package com.noti.noti.teacher.adpater.out.persistence;

import com.noti.noti.teacher.application.port.FindTeacherPort;
import com.noti.noti.teacher.domain.Teacher;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/*
The adapter implements the LoadTeacherPort(), updateActivities()
methods required by the implemented output ports.
It uses Spring Data repositories to load data from and save data to the database
and an AccountMapper to map Account domain objects into AccountJpaEntity objects
which represent an account within the database.
* */
@RequiredArgsConstructor
@Repository
public class TeacherPersistenceAdapter implements FindTeacherPort {

  private final TeacherRepository teacherRepository;
  private final TeacherMapper teacherMapper;

  /* db에 해당 social id 있는지 확인 */
  @Override
  public Teacher findBySocialId(Long social) {
    TeacherJpaEntity teacherJpaEntity = teacherRepository.findBySocialId(social)
        .orElseThrow(NoSuchElementException::new);
    Teacher teacher = teacherMapper.mapToDomainEntity(teacherJpaEntity);
    return teacher;
  }
}
