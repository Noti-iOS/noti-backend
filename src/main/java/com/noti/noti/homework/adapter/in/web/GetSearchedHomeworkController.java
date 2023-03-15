package com.noti.noti.homework.adapter.in.web;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.homework.adapter.in.web.dto.response.SearchedPageDto;
import com.noti.noti.homework.application.port.in.GetSearchedHomeworkQuery;
import com.noti.noti.homework.application.port.in.SearchedHomeworkCommand;
import com.noti.noti.homework.application.port.out.SearchedHomework;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSearchedHomeworkController {

  private final GetSearchedHomeworkQuery getSearchedHomeworkQuery;

  @GetMapping("/api/teacher/calendar/search")
  public SuccessResponse<SearchedPageDto> getSearchedHomeworkInfo(Long teacherId, @RequestParam String keyword, int size, String cursorId) {
    List<SearchedHomework> searchedHomeworkList = getSearchedHomeworkQuery.getInSearchedHomeworks(
        new SearchedHomeworkCommand(teacherId, keyword, cursorId, size));
    SearchedPageDto response = new SearchedPageDto(searchedHomeworkList);
    return SuccessResponse.create200SuccessResponse(response);
  }

}
