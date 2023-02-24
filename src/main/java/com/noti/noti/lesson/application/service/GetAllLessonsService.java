package com.noti.noti.lesson.application.service;

import com.noti.noti.lesson.application.port.in.GetAllLessonsQuery;
import com.noti.noti.lesson.application.port.out.FindLessonPort;
import com.noti.noti.lesson.application.port.out.LessonDto;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.domain.Teacher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class GetAllLessonsService implements GetAllLessonsQuery {

  private final FindTeacherPort findTeacherPort;
  private final FindLessonPort findLessonPort;

  @Transactional(readOnly = true)
  @Override
  public List<LessonDto> getAllLessons(Long teacherId) {
    Teacher teacher = findTeacher(teacherId);
    List<LessonDto> allLessons = findLessonPort.findAllLessonsByTeacherId(
        teacher.getId());

    return allLessons;
  }

  private Teacher findTeacher(Long teacherId) {
    return findTeacherPort.findById(teacherId).orElseThrow(TeacherNotFoundException::new);
  }
}
