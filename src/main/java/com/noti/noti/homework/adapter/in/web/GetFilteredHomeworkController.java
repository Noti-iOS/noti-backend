package com.noti.noti.homework.adapter.in.web;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.homework.adapter.in.web.dto.response.FilteredHomeworkDto;
import com.noti.noti.homework.application.port.in.FilteredHomeworkCommand;
import com.noti.noti.homework.application.port.in.GetFilteredHomeworkQuery;
import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class GetFilteredHomeworkController {

  private final GetFilteredHomeworkQuery getFilteredHomeworkQuery;

  @Operation(tags = "해당 분반의 숙제 수 조회 API", summary = "getFilteredHomeworkInfo", description = "해당 분반의 숙제 날짜와 숙제 수를 조회한다.",
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

    if (lessonType.equals("all")) {
      HttpHeaders headers = new HttpHeaders();
      headers.setLocation(URI.create("/api/teacher/calendar/all?year=" + year + "&month=" + month));
      return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);

    } else {
      Long filterLessonId = Long.parseLong(lessonType);
      List<InFilteredHomeworkFrequency> inFilteredHomeworkFrequencies = getFilteredHomeworkQuery.getFilteredHomeworks(
          new FilteredHomeworkCommand(teacherId, filterLessonId, year, month));
      List<FilteredHomeworkDto> filteredHomeworkDtos = new ArrayList<>();
      inFilteredHomeworkFrequencies.forEach(in -> filteredHomeworkDtos.add(new FilteredHomeworkDto(in)));

      return ResponseEntity.ok().body(SuccessResponse.create200SuccessResponse(filteredHomeworkDtos));
    }

  }
}

