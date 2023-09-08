package com.noti.noti.homework.adapter.in.web;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.homework.adapter.in.web.dto.response.FilteredHomeworkDto;
import com.noti.noti.homework.application.port.in.FilteredHomeworkCommand;
import com.noti.noti.homework.application.port.in.GetFilteredHomeworkQuery;
import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import com.noti.noti.lesson.application.port.in.DateFrequencyOfLessons;
import com.noti.noti.lesson.application.port.in.GetFrequencyOfLessonsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class GetCalendarFrequencyController {

  private final GetFilteredHomeworkQuery getFilteredHomeworkQuery;
  private final GetFrequencyOfLessonsQuery getFrequencyOfLessonsQuery;

  @Operation(tags = "해당 날짜의 숙제 또는 수업 수 조회 API", summary = "GetCalendarFrequency", description = "주어진 월에 대한 수업 수(lessonType=all) 또는 숙제 수(lessonType={수업 아이디}) 리스트를 응답한다.",
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
  @GetMapping("/api/teacher/calendar")
  @Parameter(name = "userDetails", hidden = true)
  public ResponseEntity<SuccessResponse<List<FilteredHomeworkDto>>> getFilteredHomeworkInfo(@RequestParam @Min(0) int year, @RequestParam @Range(max = 12, min = 1) int month,
      @AuthenticationPrincipal UserDetails userDetails, @RequestParam String lessonType) { // 예외처리

    long teacherId = Long.parseLong(userDetails.getUsername());

    List<FilteredHomeworkDto> filteredHomeworkDtos = new ArrayList<>();
    if (lessonType.equals("all")) {
      List<DateFrequencyOfLessons> frequencyOfLessons = getFrequencyOfLessonsQuery.findFrequencyOfLessons(
          year, month, teacherId);
      frequencyOfLessons.forEach(in -> filteredHomeworkDtos.add(new FilteredHomeworkDto(in)));
    } else {
      Long filterLessonId = Long.parseLong(lessonType);
      List<InFilteredHomeworkFrequency> inFilteredHomeworkFrequencies = getFilteredHomeworkQuery
          .getFilteredHomeworks(new FilteredHomeworkCommand(teacherId, filterLessonId, year, month));
      inFilteredHomeworkFrequencies.forEach(in -> filteredHomeworkDtos.add(new FilteredHomeworkDto(in)));
    }
    return ResponseEntity.ok().body(SuccessResponse.create200SuccessResponse(filteredHomeworkDtos));

  }
}

