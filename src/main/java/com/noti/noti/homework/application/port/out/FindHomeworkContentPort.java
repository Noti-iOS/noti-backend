package com.noti.noti.homework.application.port.out;

import java.time.LocalDateTime;
import java.util.List;

public interface FindHomeworkContentPort {

  List<OutHomeworkContent> findHomeworkContents(Long lessonId, LocalDateTime date);

}
