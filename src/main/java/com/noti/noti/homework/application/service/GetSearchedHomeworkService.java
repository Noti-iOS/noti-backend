package com.noti.noti.homework.application.service;

import com.noti.noti.homework.application.port.in.GetSearchedHomeworkQuery;
import com.noti.noti.homework.application.port.in.InSearchedPageDto;
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
  public InSearchedPageDto getSearchedHomeworks(Long teacherId, String keyword, String cursorId,
      int size) {
    List<SearchedHomework> searchedHomeworks = findSearchedHomeworkPort.findSearchedHomeworks(teacherId, keyword, size, cursorId);
    return new InSearchedPageDto(searchedHomeworks, searchedHomeworks.size() < size);
  }
}
