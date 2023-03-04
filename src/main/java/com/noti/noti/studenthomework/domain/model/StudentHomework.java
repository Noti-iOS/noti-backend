package com.noti.noti.studenthomework.domain.model;

import com.noti.noti.homework.domain.model.Homework;
import com.noti.noti.student.domain.model.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentHomework {
  private Long id;
  private boolean homeworkStatus;
  private Homework homework;
  private Student student;

  @Builder
  public StudentHomework(Long id, boolean homeworkStatus, Homework homework, Student student) {
    this.id = id;
    this.homeworkStatus = homeworkStatus;
    this.homework = homework;
    this.student = student;
  }
}
