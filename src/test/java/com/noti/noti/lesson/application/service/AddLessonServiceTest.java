package com.noti.noti.lesson.application.service;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.THURSDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.book.application.port.out.FindBookPort;
import com.noti.noti.book.domain.model.Book;
import com.noti.noti.book.exception.BookNotFoundException;
import com.noti.noti.lesson.application.port.in.AddLessonCommand;
import com.noti.noti.lesson.application.port.out.SaveLessonPort;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.lessonbook.application.port.out.SaveLessonBookPort;
import com.noti.noti.student.application.port.out.FindStudentPort;
import com.noti.noti.student.domain.model.Student;
import com.noti.noti.student.exception.StudentNotFoundException;
import com.noti.noti.studentlesson.application.port.out.SaveStudentLessonPort;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.domain.Teacher;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AddLessonServiceTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class AddLessonServiceTest {

  @InjectMocks
  AddLessonService addLessonService;
  @Mock
  FindTeacherPort findTeacherPort;
  @Mock
  FindBookPort findBookPort;
  @Mock
  SaveLessonPort saveLessonPort;
  @Mock
  SaveLessonBookPort saveLessonBookPort;
  @Mock
  FindStudentPort findStudentPort;
  @Mock
  SaveStudentLessonPort saveStudentLessonPort;

  @Nested
  class apply_메서드는 {

    final String LESSON_NAME = "MATH";
    final Long ID = 1L;

    AddLessonCommand createCommand() {
      return new AddLessonCommand(1L, LESSON_NAME, Set.of(MONDAY, THURSDAY),
          LocalTime.now(), LocalTime.now(), List.of(1L, 2L), List.of(1L, 2L));
    }

    Teacher createTeacher() {
      return Teacher.builder()
          .id(1L)
          .build();
    }

    Lesson createLesson(AddLessonCommand command, Teacher teacher) {
      return Lesson.builder()
          .id(ID)
          .lessonName(command.getLessonName())
          .startTime(command.getStartTime())
          .endTime(command.getEndTime())
          .days(command.getDays())
          .teacher(teacher)
          .build();
    }

    @Nested
    class 유효한_AddLessonCommand가_주어지면 {

      @Test
      void 성공적으로_수업을_저장하고_Lesson_객체를_반환한다() {
        AddLessonCommand addLessonCommand = createCommand();
        Teacher teacher = createTeacher();
        Lesson lesson = createLesson(addLessonCommand, teacher);

        when(findTeacherPort.findById(addLessonCommand.getTeacherId()))
            .thenReturn(Optional.of(teacher));
        when(saveLessonPort.saveLesson(any(Lesson.class))).thenReturn(lesson);
        when(findBookPort.findBookById(anyLong())).thenReturn(Optional.of(Book.builder().build()));
        when(saveLessonBookPort.saveAllLessonBooks(anyList())).thenReturn(List.of());
        when(findStudentPort.findStudentById(anyLong()))
            .thenReturn(Optional.of(Student.builder().build()));
        when(saveStudentLessonPort.saveAllStudentLessons(anyList())).thenReturn(List.of());

        Lesson savedLesson = addLessonService.apply(addLessonCommand);

        assertAll(
            () -> verify(findBookPort, times(2)).findBookById(anyLong()),
            () -> verify(findStudentPort, times(2)).findStudentById(anyLong()),
            () -> verify(saveStudentLessonPort, times(1)).saveAllStudentLessons(anyList()),
            () -> verify(saveLessonBookPort, times(1)).saveAllLessonBooks(anyList()),
            () -> assertThat(savedLesson.getLessonName()).isEqualTo(LESSON_NAME),
            () -> assertThat(savedLesson.getTeacher().getId()).isEqualTo(teacher.getId())
        );
      }
    }

    @Nested
    class 선생님_ID가_유효하지_않으면 {

      @Test
      void TeacherNotFoundException_예외가_발생한다() {
        AddLessonCommand command = createCommand();
        when(findTeacherPort.findById(command.getTeacherId())).thenThrow(
            new TeacherNotFoundException());

        assertAll(
            () -> assertThatThrownBy(() -> addLessonService.apply(command))
                .isInstanceOf(TeacherNotFoundException.class),
            () -> verify(findBookPort, never()).findBookById(anyLong()),
            () -> verify(saveLessonPort, never()).saveLesson(any()),
            () -> verify(saveLessonBookPort, never()).saveAllLessonBooks(anyList())
        );
      }
    }

    @Nested
    class 교재_ID가_유효하지_않으면 {

      @Test
      void BookNotFoundException_예외가_발생한다() {
        AddLessonCommand command = createCommand();
        Teacher teacher = createTeacher();
        Lesson lesson = createLesson(command, teacher);

        when(findTeacherPort.findById(command.getTeacherId())).thenReturn(Optional.of(teacher));
        when(saveLessonPort.saveLesson(any(Lesson.class))).thenReturn(lesson);
        when(findBookPort.findBookById(anyLong())).thenThrow(new BookNotFoundException(1L));

        assertAll(
            () -> assertThatThrownBy(() -> addLessonService.apply(command))
                .isInstanceOf(BookNotFoundException.class),
            () -> verify(saveLessonBookPort, never()).saveAllLessonBooks(anyList())
        );
      }
    }

    @Nested
    class 학생_ID가_유효하지_않으면 {

      @Test
      void StudentNotFoundException_예외가_발생한다() {
        AddLessonCommand command = createCommand();
        Teacher teacher = createTeacher();
        Lesson lesson = createLesson(command, teacher);

        when(findTeacherPort.findById(command.getTeacherId())).thenReturn(Optional.of(teacher));
        when(saveLessonPort.saveLesson(any(Lesson.class))).thenReturn(lesson);
        when(findBookPort.findBookById(anyLong())).thenReturn(Optional.of(Book.builder().build()));
        when(findStudentPort.findStudentById(anyLong())).thenThrow(
            new StudentNotFoundException(1L));

        assertAll(
            () -> assertThatThrownBy(() -> addLessonService.apply(command))
                .isInstanceOf(StudentNotFoundException.class),
            () -> verify(saveStudentLessonPort, never()).saveAllStudentLessons(anyList())
        );
      }
    }
  }

}