package com.noti.noti.homework.application.service;

import com.noti.noti.homework.application.port.in.GetSearchedHomeworkQuery;
import com.noti.noti.homework.application.port.in.SearchedHomeworkCommand;
import com.noti.noti.homework.application.port.out.FindSearchedHomeworkPort;
import com.noti.noti.homework.application.port.out.SearchedHomework;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetSearchedHomeworkService implements GetSearchedHomeworkQuery {

  private final FindSearchedHomeworkPort findSearchedHomeworkPort;

  @Override
  public List<SearchedHomework> getSearchedHomeworks(SearchedHomeworkCommand command) {
    return findSearchedHomeworkPort.findSearchedHomeworks(
        command.getTeacherId(), command.getKeyword(), command.getSize(), command.getCursorId());

  }
}
