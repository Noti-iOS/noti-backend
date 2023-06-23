package com.noti.noti.homework.application.service;

import com.noti.noti.homework.application.port.in.GetHomeworkContentQuery;
import com.noti.noti.homework.application.port.in.HomeworkContentCommand;
import com.noti.noti.homework.application.port.in.InHomeworkContent;
import com.noti.noti.homework.application.port.out.FindHomeworkContentPort;
import com.noti.noti.homework.application.port.out.OutHomeworkContent;
import com.noti.noti.lesson.application.port.out.CheckLessonExistencePort;
import com.noti.noti.lesson.exception.LessonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetHomeworkContentService implements GetHomeworkContentQuery {

  private final FindHomeworkContentPort findHomeworkContentPort;
  private final CheckLessonExistencePort checkLessonExistencePort;

  @Override
  public List<InHomeworkContent> getHomeworkContents(HomeworkContentCommand command) {

    if (!checkLessonExistencePort.existsById(command.getLessonId()))
      throw new LessonNotFoundException(command.getLessonId());

    List<OutHomeworkContent> homeworkContents = findHomeworkContentPort.findHomeworkContents(
        command.getLessonId(), command.getDate());
    List<InHomeworkContent> in = new ArrayList<>();
    homeworkContents.forEach(out -> in.add(new InHomeworkContent(out)));

    return in;
  }
}
