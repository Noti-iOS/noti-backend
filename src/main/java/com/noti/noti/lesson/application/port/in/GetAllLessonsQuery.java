package com.noti.noti.lesson.application.port.in;

import com.noti.noti.lesson.application.port.out.LessonDto;
import java.util.List;

public interface GetAllLessonsQuery {

  List<LessonDto> getAllLessons(Long teacherId);
}
