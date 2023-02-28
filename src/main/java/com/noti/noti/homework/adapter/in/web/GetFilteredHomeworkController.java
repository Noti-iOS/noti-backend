package com.noti.noti.homework.adapter.in.web;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.homework.adapter.in.web.dto.FilteredHomeworkDto;
import com.noti.noti.homework.application.port.in.FilteredHomeworkCommand;
import com.noti.noti.homework.application.port.in.GetFilteredHomeworkQuery;
import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
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
public class GetFilteredHomeworkController {

  private final GetFilteredHomeworkQuery getFilteredHomeworkQuery;

  @Operation(summary = "getFilteredHomeworkInfo", description = "해당 분반의 숙제 날짜와 숙제 수를 조회한다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성공",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)))}),
      @ApiResponse(responseCode = "500", description = "서버에러", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "400", description = "올바르지 않은 값입니다.", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @GetMapping("/api/teacher/calendar/filteredLesson")
  @Parameter(name = "userDetails", hidden = true)
  public ResponseEntity getFilteredHomeworkInfo(@RequestParam @Min(0) int year, @RequestParam @Range(max = 12, min = 1) int month, @AuthenticationPrincipal
      UserDetails userDetails,@RequestParam Long lessonId) { // 예외처리
    long teacherId = Long.parseLong(userDetails.getUsername());

    List<InFilteredHomeworkFrequency> inFilteredHomeworkFrequencies = getFilteredHomeworkQuery.getFilteredHomeworks(
        new FilteredHomeworkCommand(teacherId, lessonId, year, month));
    List<FilteredHomeworkDto> filteredHomeworkDtos = inFilteredHomeworkFrequencies.stream().map(FilteredHomeworkDto::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(SuccessResponse.create200SuccessResponse(filteredHomeworkDtos));
  }
}
