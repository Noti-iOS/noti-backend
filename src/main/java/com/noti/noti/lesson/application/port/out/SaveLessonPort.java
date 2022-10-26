package com.noti.noti.lesson.application.port.out;

import com.noti.noti.lesson.domain.model.Lesson;

public interface SaveLessonPort {

  Lesson saveLesson(Lesson lesson);
}
