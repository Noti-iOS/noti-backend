package com.noti.noti.teacher.adpater.out.persistence;

import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherNicknamePort;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.application.port.out.SaveTeacherPort;
import com.noti.noti.teacher.domain.SocialType;
import com.noti.noti.teacher.domain.Teacher;
import java.util.NoSuchElementException;
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
  public Teacher findBySocialTypeAndSocialId(SocialType socialType, Long socialId) {
    TeacherJpaEntity teacherJpaEntity = teacherRepository.findBySocialTypeAndSocialId(socialType,
            socialId)
        .orElseThrow(TeacherNotFoundException::new);
    Teacher teacher = teacherMapper.mapToDomainEntity(teacherJpaEntity);
    return teacher;
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

  /* 회원 여부 확인 */
  public boolean validate(String username, SocialType socialType) {// username=socialId

    return teacherRepository
        .findBySocialTypeAndSocialId(socialType, Long.parseLong(username)).isPresent();
//    return teacherRepository.findBySocialId(Long.parseLong(username)).isPresent();
  }

  @Override
  public String findTeacherNickname(Long teacherId) {
    return teacherQueryRepository.findTeacherNickname(teacherId);
  }
}
