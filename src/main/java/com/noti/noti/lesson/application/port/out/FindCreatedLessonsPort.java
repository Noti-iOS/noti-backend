package com.noti.noti.lesson.application.port.out;

import java.util.List;

public interface FindCreatedLessonsPort {

  List<OutCreatedLesson> findCreatedLessons(Long teacherId);

}
