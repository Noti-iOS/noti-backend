package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.lesson.adapter.in.web.dto.response.GetAllLessonsResponse;
import com.noti.noti.lesson.application.port.in.GetAllLessonsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class GetAllLessonsController {

  private final GetAllLessonsQuery getAllLessonsQuery;

  @Operation(tags = "모든 수업 조회 API ", summary = "GetAllLessons", description = "모든 수업 목록 조회",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "500", description = "서버에러", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "401", description = "인증되지 않은 유저입니다", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "403", description = "권한이 없습니다", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스입니댜", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))})
      })
  @Parameter(name = "userDetails", hidden = true)
  @GetMapping("/api/teacher/lessons")
  public ResponseEntity<SuccessResponse<List<GetAllLessonsResponse>>> getAllLessons(
      @AuthenticationPrincipal UserDetails userDetails){
    Long teacherId = Long.valueOf(userDetails.getUsername());

    ArrayList<GetAllLessonsResponse> response = new ArrayList<>();
    getAllLessonsQuery.getAllLessons(teacherId).forEach(
        lessonDto -> response.add(GetAllLessonsResponse.from(lessonDto)));

    return ResponseEntity.ok().body(SuccessResponse.create200SuccessResponse(response));
  }
}
