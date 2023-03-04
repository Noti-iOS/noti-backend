package com.noti.noti.homework.application.service;

import com.noti.noti.homework.application.port.in.FilteredHomeworkCommand;
import com.noti.noti.homework.application.port.in.GetFilteredHomeworkQuery;
import com.noti.noti.homework.application.port.in.GetHomeworkContentQuery;
import com.noti.noti.homework.application.port.in.HomeworkContentCommand;
import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.in.InHomeworkContent;
import com.noti.noti.homework.application.port.out.FindFilteredHomeworkPort;
import com.noti.noti.homework.application.port.out.FindHomeworkContentPort;
import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.OutHomeworkContent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetFilteredHomeworkService implements GetFilteredHomeworkQuery,
    GetHomeworkContentQuery {

  private final FindFilteredHomeworkPort findFilteredHomeworkPort;
  private final FindHomeworkContentPort findHomeworkContentPort;

  @Override
  public List<InFilteredHomeworkFrequency> getFilteredHomeworks(FilteredHomeworkCommand command) {
    List<OutFilteredHomeworkFrequency> outFilteredHomeworks = findFilteredHomeworkPort.findFilteredHomeworks(
        command.getDate(), command.getLessonId(), command.getTeacherId());
    return outFilteredHomeworks.stream()
        .map(InFilteredHomeworkFrequency::new)
        .collect(Collectors.toList());
  }

  @Override
  public List<InHomeworkContent> getHomeworkContents(HomeworkContentCommand command) {

    List<OutHomeworkContent> homeworkContents = findHomeworkContentPort.findHomeworkContents(
        command.getLessonId(), command.getDate());
    List<InHomeworkContent> in = new ArrayList<>();
    homeworkContents.forEach(out -> in.add(new InHomeworkContent(out)));

    return in;
  }
}

