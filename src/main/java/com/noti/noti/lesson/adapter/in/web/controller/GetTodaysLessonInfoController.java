package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.error.ErrorResponse;
import com.noti.noti.lesson.adapter.in.web.dto.response.TodaysLessonHomeworkDto;
import com.noti.noti.lesson.application.port.in.GetTodaysLessonQuery;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GetTodaysLessonInfoController {

  private final GetTodaysLessonQuery getTodaysLessonQuery;

  @Operation(summary = "todaysLessonInfo", description = "요청 선생님의 오늘에 해당하는 수업목록을 조회한다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성공",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = TodaysLessonHomeworkDto.class))}),
      @ApiResponse(responseCode = "500", description = "서버에러", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "401", description = "안증되지 않은 유저입니다", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @Parameter(name = "userDetails", hidden = true)
  @GetMapping("/api/teacher/home")
  public ResponseEntity<TodaysLessonHomeworkDto> todaysLessonInfo(
      @AuthenticationPrincipal UserDetails userDetails) {
    long teacherId = Long.parseLong((userDetails.getUsername()));

    TodaysLessonHomework todaysLessonHomework = getTodaysLessonQuery.getTodaysLessons(teacherId);

    TodaysLessonHomeworkDto todaysLessonHomeworkDto = TodaysLessonHomeworkDto.from(
        todaysLessonHomework);

    return ResponseEntity.ok(todaysLessonHomeworkDto);
  }
}
