package com.noti.noti.studentlesson.application.port.out;

import com.noti.noti.studentlesson.domain.model.StudentLesson;
import java.util.List;

public interface SaveStudentLessonPort {

  List<StudentLesson> saveAllStudentLessons(List<StudentLesson> studentLessons);
}
