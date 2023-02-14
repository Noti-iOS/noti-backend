package com.noti.noti.lesson.application.service;

import com.noti.noti.lesson.application.port.in.CreatedLessonCommand;
import com.noti.noti.lesson.application.port.in.GetCreatedLessonsQuery;
import com.noti.noti.lesson.application.port.in.InCreatedLesson;
import com.noti.noti.lesson.application.port.out.FindCreatedLessonsPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class GetCreatedLessonsService implements GetCreatedLessonsQuery {

  private final FindCreatedLessonsPort findCreatedLessonsPort;

  /**
   * 선생님이 생성한 분반 이름들을 조회
   * @param  command 선생님ID
   * @return 분반 이름
   */
  @Override
  public List<InCreatedLesson> createdLessons(CreatedLessonCommand command) {
    return findCreatedLessonsPort.findCreatedLessons(command.getTeacherId())
        .stream().map(InCreatedLesson::new).collect(Collectors.toList());
  }
}
