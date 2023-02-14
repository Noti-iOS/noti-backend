package com.noti.noti.lessonbook.application.port.out;

import com.noti.noti.lessonbook.domain.model.LessonBook;
import java.util.List;

public interface SaveLessonBookPort {

  LessonBook saveLessonBook(LessonBook lessonBook);
  List<LessonBook> saveAllLessonBooks(List<LessonBook> lessonBooks);

}
