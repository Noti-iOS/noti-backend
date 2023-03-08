package com.noti.noti.homework.adapter.in.web.controller;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.homework.adapter.in.web.request.AddHomeworksRequest;
import com.noti.noti.homework.application.port.in.AddHomeworksUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddHomeworksController {

  private final AddHomeworksUsecase addHomeworksUsecase;

  @Operation(tags = "숙제 추가 API ", summary = "addHomeworks", description = "숙제 추가",
      responses = {
          @ApiResponse(responseCode = "201", description = "성공", useReturnTypeSchema = true),
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
  @PostMapping("/api/teacher/homeworks")
  public ResponseEntity<SuccessResponse> addHomeworks(@AuthenticationPrincipal UserDetails userDetails,
      @RequestBody @Valid AddHomeworksRequest addHomeworksRequest) {
    Long teacherId = Long.valueOf(userDetails.getUsername());
    addHomeworksUsecase.addHomeworks(addHomeworksRequest.toCommand(teacherId));

    return new ResponseEntity<>(SuccessResponse.create201SuccessResponse(), HttpStatus.CREATED);
  }
}
