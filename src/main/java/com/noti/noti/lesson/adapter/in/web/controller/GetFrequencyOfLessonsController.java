package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.error.ErrorResponse;
import com.noti.noti.homework.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.homework.adapter.out.persistence.HomeworkPersistenceAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetFrequencyOfLessonsController {

  private final HomeworkPersistenceAdapter homeworkPersistenceAdapter;


  @Operation(summary = "FrequencyOfLessonsInfo", description = "요청 선생님의 월에 해당하는 수업 수와 날짜를 조회한다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성공",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = FrequencyOfLessonsDto.class)))}),
      @ApiResponse(responseCode = "500", description = "서버에러", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "400", description = "올바르지 않은 값입니다.", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @Parameter(name = "userDetails", hidden = true)
  @GetMapping("/api/teacher/calendar/{year}/{month}")
  public ResponseEntity<List<FrequencyOfLessonsDto>> getFrequencyOfLessons(
      @PathVariable String year, @PathVariable String month,
      @AuthenticationPrincipal UserDetails userDetails) {

    long teacherId = Long.parseLong(userDetails.getUsername());
    String yearMonth = new StringBuilder().append(year).append("-").append(month).toString();
    List<FrequencyOfLessonsDto> responseDto = homeworkPersistenceAdapter.findFrequencyOfLessons(yearMonth, teacherId);

    return ResponseEntity.ok(responseDto);
  }


}
