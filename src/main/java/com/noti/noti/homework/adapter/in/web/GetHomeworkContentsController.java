package com.noti.noti.homework.adapter.in.web;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.homework.adapter.in.web.dto.response.HomeworkContentDto;
import com.noti.noti.homework.application.port.in.GetHomeworkContentQuery;
import com.noti.noti.homework.application.port.in.HomeworkContentCommand;
import com.noti.noti.homework.application.port.in.InHomeworkContent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


  @Operation(tags = "필터 적용시 숙제 목록 조회 API", summary = "getHomeworkContentInfo", description = "캘린더에서 분반이 적용된 후 날짜에 해당하는 숙제 목록을 조회한다.",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "500", description = "서버에러", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "401", description = "안증되지 않은 유저입니다", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "400", description = "올바르지 않은 값입니다.", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))})
      })
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
