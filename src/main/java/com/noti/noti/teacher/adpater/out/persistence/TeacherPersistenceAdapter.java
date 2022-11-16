package com.noti.noti.teacher.adpater.out.persistence;

import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.application.port.out.SaveTeacherPort;
import com.noti.noti.teacher.domain.Teacher;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class TeacherPersistenceAdapter implements FindTeacherPort, SaveTeacherPort {

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


  /* 선생님 저장 */
  @Override
  public TeacherJpaEntity saveTeacher(Teacher teacher) {
    TeacherJpaEntity teacherJpaEntity = teacherMapper.mapToJpaEntity(teacher);
    TeacherJpaEntity newTeacher = teacherRepository.save(teacherJpaEntity);
    return newTeacher;
  }
}
