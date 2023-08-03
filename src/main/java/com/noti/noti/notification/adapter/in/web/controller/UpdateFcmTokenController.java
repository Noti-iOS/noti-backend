package com.noti.noti.notification.adapter.in.web.controller;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.notification.adapter.in.web.dto.request.UpdateFcmTokenRequest;
import com.noti.noti.notification.application.port.in.UpdateFcmTokenCommand;
import com.noti.noti.notification.application.port.in.UpdateFcmTokenUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 토큰 만료기간 갱신 요청시 사용할 api
 */
@RestController
@RequiredArgsConstructor
public class UpdateFcmTokenController {

  private final UpdateFcmTokenUsecase updateFcmTokenUsecase;

  @Operation(tags = "FCM Token 만료기간 갱신 API ", summary = "updateFcmToken", description = "FCM Token 만료기간 갱신",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "500", description = "서버에러", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "400", description = "파라미터 에러", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "404", description = "리소스 에러", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
      })
  @PatchMapping("/api/notification/tokens")
  public ResponseEntity<SuccessResponse> updateFcmToken(
      @Valid @RequestBody UpdateFcmTokenRequest updateFcmTokenRequest) {
    updateFcmTokenUsecase.updateFcmToken(
        new UpdateFcmTokenCommand(updateFcmTokenRequest.getFcmToken()));
    return ResponseEntity.ok(SuccessResponse.create200SuccessResponse());
  }
}
