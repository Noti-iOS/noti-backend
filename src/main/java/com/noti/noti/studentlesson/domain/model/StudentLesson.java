package com.noti.noti.studentlesson.domain.model;

import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.student.domain.model.Student;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudentLesson {

  private Long id;
  private boolean focusStatus;
  private boolean isDeleted;
  private Lesson lesson;
  private Student student;

  @Builder
  public StudentLesson(Long id, boolean focusStatus, boolean isDeleted, Lesson lesson,
      Student student) {
    this.id = id;
    this.focusStatus = focusStatus;
    this.isDeleted = isDeleted;
    this.lesson = lesson;
    this.student = student;
  }
}
