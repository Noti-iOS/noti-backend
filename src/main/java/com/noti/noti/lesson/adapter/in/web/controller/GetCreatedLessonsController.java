package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.lesson.adapter.in.web.dto.response.CreatedLessonsDto;
import com.noti.noti.lesson.application.port.in.CreatedLessonCommand;
import com.noti.noti.lesson.application.port.in.GetCreatedLessonsQuery;
import com.noti.noti.lesson.application.port.in.InCreatedLesson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GetCreatedLessonsController {

  private final GetCreatedLessonsQuery getCreatedLessonsQuery;

  @Operation(tags = "생성한 분반 조회 API", summary = "CreatedLessonInfo", description = "선생님이 생성한 분반을 리스트로 제공한다.",
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
  @GetMapping("/api/teacher/calendar/createdLessons")
  @Parameter(name = "UserDetails", hidden = true)
  public ResponseEntity<SuccessResponse<List<CreatedLessonsDto>>> getLessonsByTeacherId(@AuthenticationPrincipal
      UserDetails userDetails) {
    Long teacherId = Long.parseLong(userDetails.getUsername());

    List<InCreatedLesson> inCreatedLessons = getCreatedLessonsQuery.createdLessons(new CreatedLessonCommand(teacherId));
    List<CreatedLessonsDto> createdLessonsDto = inCreatedLessons.stream().map(CreatedLessonsDto::new).collect(Collectors.toList());

    return ResponseEntity.ok(SuccessResponse.create200SuccessResponse(createdLessonsDto));
  }


}
