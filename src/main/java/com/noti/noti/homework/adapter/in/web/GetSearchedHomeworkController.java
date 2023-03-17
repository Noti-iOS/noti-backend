package com.noti.noti.homework.adapter.in.web;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.homework.adapter.in.web.dto.request.SearchedHomeworkRequest;
import com.noti.noti.homework.adapter.in.web.dto.response.SearchedPageDto;
import com.noti.noti.homework.application.port.in.GetSearchedHomeworkQuery;
import com.noti.noti.homework.application.port.in.InSearchedPageDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSearchedHomeworkController {

  private final GetSearchedHomeworkQuery getSearchedHomeworkQuery;

  @GetMapping("/api/teacher/calendar/search")
  public SuccessResponse<SearchedPageDto> getSearchedHomeworkInfo(@RequestBody @Valid SearchedHomeworkRequest request) {

    InSearchedPageDto searchedHomeworks = getSearchedHomeworkQuery.getSearchedHomeworks(request.getTeacherId(), request.getKeyword(), request.getCursorId(), request.getSize());
    SearchedPageDto response = new SearchedPageDto(searchedHomeworks);

    return SuccessResponse.create200SuccessResponse(response);
  }

}
