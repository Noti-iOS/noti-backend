package com.noti.noti.homework.adapter.in.web;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.homework.adapter.in.web.dto.response.HomeworkContentDto;
import com.noti.noti.homework.application.port.in.GetHomeworkContentQuery;
import com.noti.noti.homework.application.port.in.HomeworkContentCommand;
import com.noti.noti.homework.application.port.in.InHomeworkContent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GetHomeworkContentsController {

  private final GetHomeworkContentQuery getHomeworkContentQuery;


  @GetMapping("/api/teacher/calendar/filtered/content")
  public ResponseEntity<SuccessResponse<List<HomeworkContentDto>>> getHomeworkContentInfo(@RequestParam Long lessonId,
      @RequestParam String date) {

    List<InHomeworkContent> homeworkContents = getHomeworkContentQuery.getHomeworkContents(
        new HomeworkContentCommand(lessonId, date));

    List<HomeworkContentDto> homeworkContentDtos = new ArrayList<>();
    homeworkContents.forEach(
        inHomeworkContent -> homeworkContentDtos.add(new HomeworkContentDto(inHomeworkContent)));

    return ResponseEntity.ok().body(SuccessResponse.create200SuccessResponse(homeworkContentDtos));

  }


}
