package com.noti.noti.teacher.adpater.out.persistence;

import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherNicknamePort;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.application.port.out.SaveTeacherPort;
import com.noti.noti.teacher.domain.SocialType;
import com.noti.noti.teacher.domain.Teacher;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class TeacherPersistenceAdapter implements FindTeacherPort, SaveTeacherPort,
    FindTeacherNicknamePort {

  private final TeacherRepository teacherRepository;
  private final TeacherQueryRepository teacherQueryRepository;
  private final TeacherMapper teacherMapper;

  /* db에 해당 social id 있는지 확인 */
  @Override
  public Teacher findBySocialId(Long social) {
    TeacherJpaEntity teacherJpaEntity = teacherRepository.findBySocialId(social)
        .orElseThrow(TeacherNotFoundException::new);
    Teacher teacher = teacherMapper.mapToDomainEntity(teacherJpaEntity);
    return teacher;
  }

  @Override
  public Optional<Teacher> findBySocialTypeAndSocialId(SocialType socialType, String socialId) {
    return teacherRepository.findBySocialTypeAndSocialId(socialType, socialId)
        .map(teacherMapper::mapToDomainEntity);
  }

  @Override
  public Teacher findById(Long id) {
    TeacherJpaEntity teacherJpaEntity = teacherRepository.findById(id)
        .orElseThrow(() -> new TeacherNotFoundException(id));
    Teacher teacher = teacherMapper.mapToDomainEntity(teacherJpaEntity);
    return teacher;
  }


  /* 선생님 저장 */
  @Override
  public Teacher saveTeacher(Teacher teacher) {
    TeacherJpaEntity teacherJpaEntity = teacherMapper.mapToJpaEntity(teacher);
    TeacherJpaEntity newTeacher = teacherRepository.save(teacherJpaEntity);

    return teacherMapper.mapToDomainEntity(newTeacher);
  }

  @Override
  public String findTeacherNickname(Long teacherId) {
    return teacherQueryRepository.findTeacherNickname(teacherId);
  }
}
