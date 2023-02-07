package com.noti.noti.lesson.application.port.in;

import java.util.List;

public interface GetCreatedLessonsQuery {

  List<InCreatedLesson> createdLessons(CreatedLessonCommand command);

}
