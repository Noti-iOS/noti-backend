package com.noti.noti.homework.application.service;

import com.noti.noti.homework.application.port.in.AddHomeworksCommand;
import com.noti.noti.homework.application.port.in.AddHomeworksUsecase;
import com.noti.noti.homework.application.port.out.SaveDeadlineAlarmPort;
import com.noti.noti.homework.application.port.out.SaveHomeworkPort;
import com.noti.noti.homework.domain.model.Homework;
import com.noti.noti.lesson.application.exception.LessonNotFoundException;
import com.noti.noti.lesson.application.port.out.FindLessonPort;
import com.noti.noti.lesson.application.port.out.StudentsInLesson;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.student.domain.model.Student;
import com.noti.noti.studenthomework.application.port.out.SaveStudentHomeworkPort;
import com.noti.noti.studenthomework.domain.model.StudentHomework;
import com.noti.noti.teacher.domain.Teacher;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 숙제를 추가하는 서비스 클래스 입니다. 숙제 존재 여부와 숙제에 해당하는 학생의 id 목록을 조회하고 숙제가 유효하면 요청 받은 숙제추가 정보로 숙제를 생성합니다. 숙제를
 * 생성한 이후에 StudentHomework를 저장하고 알람시간를 세팅했다면 숙제의 알람 정보를 저장합니다.
 *
 * @author 임호준
 */
@Service
@RequiredArgsConstructor
class AddHomeworksService implements AddHomeworksUsecase {

  private final FindLessonPort findLessonPort;
  private final SaveHomeworkPort saveHomeworkPort;
  private final SaveStudentHomeworkPort saveStudentHomeworkPort;
  private final SaveDeadlineAlarmPort saveDeadlineAlarmPort;

  @Transactional
  @Override
  public void addHomeworks(AddHomeworksCommand addHomeworksCommand) {
    StudentsInLesson studentsInLesson = findLessonAndStudent(addHomeworksCommand.getLessonId());

    List<Long> savedHomeworkIds = saveHomeworkPort.saveAllHomeworks(
        generateHomeworks(addHomeworksCommand));

    saveAllStudentHomeworks(studentsInLesson, savedHomeworkIds);

    saveAlarm(addHomeworksCommand, savedHomeworkIds);
  }

  /**
   * 숙제 도메인 리스트를 생성하는 메서드
   * @param addHomeworksCommand 숙제추가 command 클래스
   * @return Homework 리스트를 반환
   */
  private List<Homework> generateHomeworks(AddHomeworksCommand addHomeworksCommand) {
    return addHomeworksCommand.getHomeworkNames().stream()
        .map(homeworkName -> Homework.builder()
            .homeworkName(homeworkName)
            .endTime(addHomeworksCommand.getEndTime())
            .startTime(addHomeworksCommand.getStartTime())
            .lesson(Lesson.builder().id(addHomeworksCommand.getLessonId())
                .teacher(Teacher.builder()
                    .id(addHomeworksCommand.getTeacherId()).build()).build())
            .build())
        .collect(Collectors.toList());
  }

  /**
   * student id와 homework id에 해당하는 StudentHomework 목록 생성 메서드
   * @param studentIds 학생 id 리스트
   * @param homeworkIds 숙제 id 리스트
   * @return StudentHomework 리스트
   */
  private List<StudentHomework> generateStudentHomeworks(List<Long> studentIds,
      List<Long> homeworkIds) {
    return studentIds.stream()
        .flatMap(studentId -> homeworkIds.stream()
            .map(homeworkId -> StudentHomework.builder()
                .student(Student.builder().id(studentId).build())
                .homework(Homework.builder().id(homeworkId).build())
                .build()))
        .collect(Collectors.toList());
  }

  /**
   * 수업의 학생 목록의 조회 결과에서 student id 리스트가 비어있지 않으면 studentHomework를 생성해 저장하는 메서드
   * @param studentsInLesson 수업의 학생
   * @param savedHomeworkIds 저장된 숙제의 id 리스트
   */
  private void saveAllStudentHomeworks(StudentsInLesson studentsInLesson,
      List<Long> savedHomeworkIds) {
    if (!studentsInLesson.getStudentIds().isEmpty()) {
      saveStudentHomeworkPort.saveAllStudentHomeworks(
          generateStudentHomeworks(studentsInLesson.getStudentIds(), savedHomeworkIds));
    }
  }

  /**
   * 알람시간이 세팅되어 있으면 숙제의 알람정보를 저장하는 메서드
   * @param addHomeworksCommand 숙제 생성 command
   * @param homeworkIds 숙제 id 리스트
   */
  private void saveAlarm(AddHomeworksCommand addHomeworksCommand, List<Long> homeworkIds) {
    if (addHomeworksCommand.getDeadlineAlarmSettingTime() != 0) {
      for (Long homeworkId : homeworkIds) {
        saveDeadlineAlarmPort.saveDeadlineAlarm(homeworkId,
            addHomeworksCommand.getEndTime().minusHours(
                addHomeworksCommand.getDeadlineAlarmSettingTime()));
      }
    }
  }

  /**
   * 수업 id로 수업의 존재여부와 수업의 학생 id를 조회하는 메서드
   * @param lessonId 수업의 id
   * @return 수업의 id와 수업에 해당하는 학생 id 리스트를 가지는 class
   * @throws LessonNotFoundException
   *         수업 id에 해당하는 수업이 존재하지 않는 경우
   */
  private StudentsInLesson findLessonAndStudent(Long lessonId) {
    return findLessonPort.findLessonAndStudentsById(lessonId)
        .orElseThrow(LessonNotFoundException::new);
  }
}
