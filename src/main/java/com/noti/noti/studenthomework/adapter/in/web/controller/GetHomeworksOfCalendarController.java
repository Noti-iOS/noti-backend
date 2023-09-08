package com.noti.noti.studenthomework.adapter.in.web.controller;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.studenthomework.adapter.in.web.dto.response.HomeworkOfGivenDateDto;
import com.noti.noti.studenthomework.application.port.in.GetHomeworksOfCalendarQuery;
import com.noti.noti.studenthomework.application.port.in.InHomeworkOfGivenDate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GetHomeworksOfCalendarController {

  private final GetHomeworksOfCalendarQuery getHomeworksOfCalendarQuery;

  @Operation(tags = "날짜에 해당하는 수업, 숙제 목록 조회 API", summary = "getHomeworksOfCalendar", description = "요청 날짜에 해당하는 수업목록 및 숙제목록을 조회한다.",
  responses = {
      @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
      @ApiResponse(responseCode = "500", description = "서버에러", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "401", description = "인증되지 않은 유저입니다", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "400", description = "올바르지 않은 값입니다.", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @GetMapping("/api/teacher/calendar/{year}/{month}/{day}")
  @Parameter(name = "userDetails", hidden = true)
  ResponseEntity<SuccessResponse<List<HomeworkOfGivenDateDto>>> getHomeworksOfCalendar(
      @Min(1) @PathVariable int year, @Min(1) @Max(12) @PathVariable int month, @PathVariable int day,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    long teacherId = Long.parseLong(userDetails.getUsername());
    LocalDate date = LocalDate.of(year, month, day);
    List<InHomeworkOfGivenDate> inHomeworkOfGivenDate = getHomeworksOfCalendarQuery.findHomeworksOfCalendar(
        date, teacherId);
    List<HomeworkOfGivenDateDto> responseDto = new ArrayList<>();
    inHomeworkOfGivenDate.forEach(
        homeworkOfGivenDate -> responseDto.add(
            new HomeworkOfGivenDateDto(homeworkOfGivenDate.getLessonId(),
                homeworkOfGivenDate.getLessonName(),
                homeworkOfGivenDate.getStartTimeOfLesson(),
                homeworkOfGivenDate.getEndTimeOfLesson(),
                homeworkOfGivenDate.getHomeworks())
        )
    );

    return ResponseEntity.ok(SuccessResponse.create200SuccessResponse(responseDto));
  }

}
