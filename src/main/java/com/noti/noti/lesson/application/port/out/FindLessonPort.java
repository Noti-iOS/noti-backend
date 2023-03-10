package com.noti.noti.lesson.application.port.out;

import com.noti.noti.lesson.domain.model.Lesson;
import java.util.List;
import java.util.Optional;

public interface FindLessonPort {

  Optional<Lesson> findById(Long id);
  List<TodaysLesson> findTodaysLessons(TodaysLessonSearchConditon condition);
  List<LessonDto> findAllLessonsByTeacherId(Long teacherId);
  Optional<StudentsInLesson> findLessonAndStudentsById(Long lessonId);
}
