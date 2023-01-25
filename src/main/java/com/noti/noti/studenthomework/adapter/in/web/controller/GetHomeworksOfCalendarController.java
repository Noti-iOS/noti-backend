package com.noti.noti.studenthomework.adapter.in.web.controller;

import com.noti.noti.error.ErrorResponse;
import com.noti.noti.studenthomework.adapter.in.web.dto.HomeworkOfGivenDateDto;
import com.noti.noti.studenthomework.adapter.out.persistence.StudentHomeworkAdapter;
import com.noti.noti.studenthomework.application.port.in.GetHomeworksOfCalendarQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDate;
import java.util.List;
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

  @Operation(summary = "HomeworksOfCalendarInfo", description = "요청 선생님의 날짜에 해당하는 수업목록 및 숙제목록을 조회한다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성공",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = HomeworkOfGivenDateDto.class)))}),
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
  @GetMapping("/api/teacher/calendar/{year}/{month}/{day}")
  @Parameter(name = "userDetails", hidden = true)
  ResponseEntity<List<HomeworkOfGivenDateDto>> getHomeworksOfCalendar(
      @PathVariable int year, @PathVariable int month, @PathVariable int day,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    long teacherId = Long.parseLong(userDetails.getUsername());
    LocalDate date = LocalDate.of(year, month, day);
    List<HomeworkOfGivenDateDto> responseDto = getHomeworksOfCalendarQuery.findHomeworksOfCalendar(date, teacherId);

    return ResponseEntity.ok(responseDto);
  }

}