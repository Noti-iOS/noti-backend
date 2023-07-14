package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.lesson.adapter.in.web.dto.response.FrequencyOfLessonsDto;
import com.noti.noti.lesson.application.port.in.DateFrequencyOfLessons;
import com.noti.noti.lesson.application.port.in.GetFrequencyOfLessonsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class GetFrequencyOfLessonsController {

  private final GetFrequencyOfLessonsQuery getFrequencyOfLessonsQuery;

  @Operation(tags = "날짜 별 수업 수 조회 API", summary = "FrequencyOfLessonsInfo", description = "요청 선생님의 월에 해당하는 수업 수와 날짜를 조회한다.",
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
  @Parameter(name = "userDetails", hidden = true)
  @GetMapping("/api/teacher/calendar/all")
  public ResponseEntity<SuccessResponse<List<FrequencyOfLessonsDto>>> getFrequencyOfLessons(
      @RequestParam @Min(1) int year, @Min(1) @Max(12) @RequestParam int month,
      @AuthenticationPrincipal UserDetails userDetails) {

    long teacherId = Long.parseLong(userDetails.getUsername());
    String yearMonth;

    StringBuilder sb = new StringBuilder();
    if (month < 10) {
      yearMonth = sb.append(year).append("-0").append(month).toString();
      System.out.println("yearMonth = " + yearMonth);
    } else {
      yearMonth = sb.append(year).append("-").append(month).toString();
    }

    List<DateFrequencyOfLessons> frequencyOfLessons = getFrequencyOfLessonsQuery.findFrequencyOfLessons(yearMonth, teacherId);
    List<FrequencyOfLessonsDto> frequencyOfLessonsDto = new ArrayList<>();
    frequencyOfLessons.forEach(
        dateFrequencyOfLessons -> frequencyOfLessonsDto.add(
            new FrequencyOfLessonsDto(dateFrequencyOfLessons.getDateOfLesson(), dateFrequencyOfLessons.getFrequencyOfLesson())
        )
    );

    return ResponseEntity.ok(SuccessResponse.create200SuccessResponse(frequencyOfLessonsDto));
  }


}
