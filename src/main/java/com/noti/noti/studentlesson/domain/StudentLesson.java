package com.noti.noti.studentlesson.domain;

import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.student.domain.model.Student;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudentLesson {

  private Long id;
  private boolean focusStatus;
  private Lesson lesson;
  private Student student;

  @Builder
  public StudentLesson(Long id, boolean focusStatus, Lesson lesson, Student student) {
    this.id = id;
    this.focusStatus = focusStatus;
    this.lesson = lesson;
    this.student = student;
  }
}
