package com.noti.noti.homework.application.service;

import com.noti.noti.homework.application.port.in.FilteredHomeworkCommand;
import com.noti.noti.homework.application.port.in.GetFilteredHomeworkQuery;
import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.FindFilteredHomeworkPort;
import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetFilteredHomeworkService implements GetFilteredHomeworkQuery {

  private final FindFilteredHomeworkPort findFilteredHomeworkPort;

  @Override
  public List<InFilteredHomeworkFrequency> getFilteredHomeworks(FilteredHomeworkCommand command) {
    List<OutFilteredHomeworkFrequency> outFilteredHomeworks = findFilteredHomeworkPort.findFilteredHomeworks(
        command.getDate(), command.getLessonId(), command.getTeacherId());
    return outFilteredHomeworks.stream()
        .map(InFilteredHomeworkFrequency::new)
        .collect(Collectors.toList());
  }
}

