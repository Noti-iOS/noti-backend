package com.noti.noti.homework.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.homework.application.port.in.AddHomeworksCommand;
import com.noti.noti.homework.application.port.out.SaveDeadlineAlarmPort;
import com.noti.noti.homework.application.port.out.SaveHomeworkPort;
import com.noti.noti.lesson.application.exception.LessonNotFoundException;
import com.noti.noti.lesson.application.port.out.FindLessonPort;
import com.noti.noti.lesson.application.port.out.StudentsInLesson;
import com.noti.noti.studenthomework.application.port.out.SaveStudentHomeworkPort;
import java.util.List;
import java.util.Optional;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("AddHomeworkServiceTest 클래스")
class AddHomeworksServiceTest {

  @InjectMocks
  AddHomeworksService addHomeworksService;

  @Mock
  FindLessonPort findLessonPort;

  @Mock
  SaveHomeworkPort saveHomeworkPort;

  @Mock
  SaveStudentHomeworkPort saveStudentHomeworkPort;

  @Mock
  SaveDeadlineAlarmPort saveDeadlineAlarmPort;

  @Nested
  class addHomeworks_메서드는 {

    @Nested
    class 유효하지_않는_수업_id가_주어지면 {

      @Test
      void LessonNotFoundException_예외가_발생한다() {
        AddHomeworksCommand command = MonkeyUtils.MONKEY.giveMeBuilder(AddHomeworksCommand.class)
            .setNotNull("lessonId").sample();
        when(findLessonPort.findLessonAndStudentsById(command.getLessonId())).thenReturn(
            Optional.empty());

        assertAll(
            () -> assertThatThrownBy(() -> addHomeworksService.addHomeworks(command)).isInstanceOf(
                LessonNotFoundException.class),
            () -> verify(saveHomeworkPort, never()).saveAllHomeworks(anyList()),
            () -> verify(saveStudentHomeworkPort, never()).saveAllStudentHomeworks(anyList()),
            () -> verify(saveDeadlineAlarmPort, never()).saveDeadlineAlarm(any(), any())
        );
      }
    }

    @Nested
    class 수업에_해당하는_학생이_존재하지_않고_알람_설정이_되어있지_않으면 {

      @Test
      void 요청된_숙제_정보를_저장하고_StudentHomework과_알람_정보는_저장하지_않는다() {
        AddHomeworksCommand command =
            MonkeyUtils.MONKEY.giveMeBuilder(AddHomeworksCommand.class)
                .setNotNull("lessonId")
                .setNotNull("teacherId")
                .size("homeworkNames", 3)
                .set("deadlineAlarmSettingTime", 0L)
                .sample();

        StudentsInLesson studentsInLesson =
            MonkeyUtils.MONKEY.giveMeBuilder(StudentsInLesson.class)
                .set("lessonId", command.getLessonId())
                .size("studentIds", 0)
                .sample();

        List<Long> savedHomeworkIds = List.of(1L, 2L, 3L);

        when(findLessonPort.findLessonAndStudentsById(command.getLessonId())).thenReturn(
            Optional.of(studentsInLesson));
        when(saveHomeworkPort.saveAllHomeworks(anyList())).thenReturn(savedHomeworkIds);

        addHomeworksService.addHomeworks(command);
        assertAll(
            () -> verify(findLessonPort, times(1)).findLessonAndStudentsById(
                command.getLessonId()),
            () -> verify(saveHomeworkPort, atLeastOnce()).saveAllHomeworks(anyList()),
            () -> verify(saveStudentHomeworkPort, never()).saveAllStudentHomeworks(anyList()),
            () -> verify(saveDeadlineAlarmPort, never()).saveDeadlineAlarm(any(), any())
        );
      }
    }

    @Nested
    class 수업에_해당하는_학생이_존재하지_않고_알람_설정이_되어있으면 {

      @Test
      void 숙제와_숙제의_알람_정보만_저장한다() {
        AddHomeworksCommand command =
            MonkeyUtils.MONKEY.giveMeBuilder(AddHomeworksCommand.class)
                .setNotNull("lessonId")
                .setNotNull("teacherId")
                .setNotNull("endTime")
                .size("homeworkNames", 3)
                .set("deadlineAlarmSettingTime", 1L)
                .sample();

        StudentsInLesson studentsInLesson =
            MonkeyUtils.MONKEY.giveMeBuilder(StudentsInLesson.class)
                .set("lessonId", command.getLessonId())
                .size("studentIds", 0)
                .sample();

        List<Long> savedHomeworkIds = List.of(1L, 2L, 3L);

        when(findLessonPort.findLessonAndStudentsById(command.getLessonId())).thenReturn(
            Optional.of(studentsInLesson));
        when(saveHomeworkPort.saveAllHomeworks(anyList())).thenReturn(savedHomeworkIds);
        doNothing().when(saveDeadlineAlarmPort).saveDeadlineAlarm(anyLong(), any());

        addHomeworksService.addHomeworks(command);
        assertAll(
            () -> verify(findLessonPort, times(1)).findLessonAndStudentsById(
                command.getLessonId()),
            () -> verify(saveHomeworkPort, atLeastOnce()).saveAllHomeworks(anyList()),
            () -> verify(saveStudentHomeworkPort, never()).saveAllStudentHomeworks(anyList()),
            () -> verify(saveDeadlineAlarmPort, atLeastOnce()).saveDeadlineAlarm(any(), any())
        );
      }
    }

    @Nested
    class 수업에_해당하는_학생이_존재하고_알람_설정이_되어있으면 {

      @Test
      void 숙제와_숙제의_알람_정보_StudentHomework를_저장한다() {
        AddHomeworksCommand command =
            MonkeyUtils.MONKEY.giveMeBuilder(AddHomeworksCommand.class)
                .setNotNull("lessonId")
                .setNotNull("teacherId")
                .setNotNull("endTime")
                .size("homeworkNames", 3)
                .set("deadlineAlarmSettingTime", 1L)
                .sample();

        List<Long> studentIds =
            Arbitraries.longs()
                .between(1L, 10L).list().ofSize(5).uniqueElements()
                .sample();

        StudentsInLesson studentsInLesson =
            MonkeyUtils.MONKEY.giveMeBuilder(StudentsInLesson.class)
                .set("lessonId", command.getLessonId())
                .set("studentIds", studentIds)
                .sample();

        List<Long> savedHomeworkIds = List.of(1L, 2L, 3L);

        when(findLessonPort.findLessonAndStudentsById(command.getLessonId())).thenReturn(
            Optional.of(studentsInLesson));
        when(saveHomeworkPort.saveAllHomeworks(anyList())).thenReturn(savedHomeworkIds);
        doNothing().when(saveStudentHomeworkPort).saveAllStudentHomeworks(anyList());
        doNothing().when(saveDeadlineAlarmPort).saveDeadlineAlarm(anyLong(), any());

        addHomeworksService.addHomeworks(command);
        assertAll(
            () -> verify(findLessonPort, times(1)).findLessonAndStudentsById(
                command.getLessonId()),
            () -> verify(saveHomeworkPort, atLeastOnce()).saveAllHomeworks(anyList()),
            () -> verify(saveStudentHomeworkPort, times(1)).saveAllStudentHomeworks(anyList()),
            () -> verify(saveDeadlineAlarmPort, atLeastOnce()).saveDeadlineAlarm(any(), any())
        );
      }
    }

    @Nested
    class 수업에_해당하는_학생이_존재하고_알람_설정이_되어있지_않으면 {

      @Test
      void 숙제와_StudentHomework를_저장한다() {
        AddHomeworksCommand command =
            MonkeyUtils.MONKEY.giveMeBuilder(AddHomeworksCommand.class)
                .setNotNull("lessonId")
                .setNotNull("teacherId")
                .size("homeworkNames", 3)
                .set("deadlineAlarmSettingTime", 0L)
                .sample();

        List<Long> studentIds =
            Arbitraries.longs()
                .between(1L, 10L).list().ofSize(5).uniqueElements()
                .sample();

        StudentsInLesson studentsInLesson =
            MonkeyUtils.MONKEY.giveMeBuilder(StudentsInLesson.class)
                .set("lessonId", command.getLessonId())
                .set("studentIds", studentIds)
                .sample();

        List<Long> savedHomeworkIds = List.of(1L, 2L, 3L);

        when(findLessonPort.findLessonAndStudentsById(command.getLessonId())).thenReturn(
            Optional.of(studentsInLesson));
        when(saveHomeworkPort.saveAllHomeworks(anyList())).thenReturn(savedHomeworkIds);
        doNothing().when(saveStudentHomeworkPort).saveAllStudentHomeworks(anyList());

        addHomeworksService.addHomeworks(command);
        assertAll(
            () -> verify(findLessonPort, times(1)).findLessonAndStudentsById(
                command.getLessonId()),
            () -> verify(saveHomeworkPort, atLeastOnce()).saveAllHomeworks(anyList()),
            () -> verify(saveStudentHomeworkPort, times(1)).saveAllStudentHomeworks(anyList()),
            () -> verify(saveDeadlineAlarmPort, never()).saveDeadlineAlarm(any(), any())
        );
      }
    }
  }
}