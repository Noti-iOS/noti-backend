package com.noti.noti.lesson.application.service;

import com.noti.noti.book.application.port.out.FindBookPort;
import com.noti.noti.book.domain.model.Book;
import com.noti.noti.book.exception.BookNotFoundException;
import com.noti.noti.lesson.application.port.in.AddLessonCommand;
import com.noti.noti.lesson.application.port.in.AddLessonUsecase;
import com.noti.noti.lesson.application.port.out.SaveLessonPort;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.lessonbook.application.port.out.SaveLessonBookPort;
import com.noti.noti.lessonbook.domain.model.LessonBook;
import com.noti.noti.student.application.port.out.FindStudentPort;
import com.noti.noti.student.domain.model.Student;
import com.noti.noti.student.exception.StudentNotFoundException;
import com.noti.noti.studentlesson.application.port.out.SaveStudentLessonPort;
import com.noti.noti.studentlesson.domain.model.StudentLesson;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.domain.Teacher;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddLessonService implements AddLessonUsecase {

  private final FindTeacherPort findTeacherPort;
  private final SaveLessonPort saveLessonPort;
  private final SaveLessonBookPort saveLessonBookPort;
  private final FindBookPort findBookPort;
  private final FindStudentPort findStudentPort;
  private final SaveStudentLessonPort saveStudentLessonPort;

  @Transactional
  @Override
  public Lesson apply(AddLessonCommand addLessonCommand) {

    Teacher teacher = findTeacherPort.findById(addLessonCommand.getTeacherId())
        .orElseThrow(TeacherNotFoundException::new);

    Lesson savedLesson = saveLesson(addLessonCommand, teacher);

    saveLessonBooks(addLessonCommand.getBookIds(), savedLesson);

    saveStudentLesson(addLessonCommand.getStudentIds(), savedLesson);

    return savedLesson;
  }

  private Lesson saveLesson(AddLessonCommand addLessonCommand, Teacher teacher) {
    Lesson lesson = Lesson.builder()
        .lessonName(addLessonCommand.getLessonName())
        .days(addLessonCommand.getDays())
        .startTime(addLessonCommand.getStartTime())
        .endTime(addLessonCommand.getEndTime())
        .teacher(teacher)
        .build();

    Lesson savedLesson = saveLessonPort.saveLesson(lesson);

    return savedLesson;
  }

  private void saveLessonBooks(List<Long> bookIds, Lesson lesson) {
    List<LessonBook> lessonBooks = new ArrayList<>();

    for (Long bookId : bookIds) {
      Book book = findBookPort.findBookById(bookId)
          .orElseThrow(() -> new BookNotFoundException(bookId));
      lessonBooks.add(LessonBook.builder().lesson(lesson).book(book).build());
    }

    saveLessonBookPort.saveAllLessonBooks(lessonBooks);
  }

  private void saveStudentLesson(List<Long> studentIds, Lesson lesson) {
    List<StudentLesson> studentLessons = new ArrayList<>();

    for (Long studentId : studentIds) {
      Student student = findStudentPort.findStudentById(studentId)
          .orElseThrow(() -> new StudentNotFoundException(studentId));
      studentLessons.add(StudentLesson.builder().lesson(lesson).student(student).build());
    }

    saveStudentLessonPort.saveAllStudentLessons(studentLessons);
  }
}
