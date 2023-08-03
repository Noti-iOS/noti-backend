package com.noti.noti.notification.adapter.in.web.controller;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.notification.adapter.in.web.dto.request.SaveFcmTokenRequest;
import com.noti.noti.notification.application.port.in.SaveFcmTokenCommand;
import com.noti.noti.notification.application.port.in.SaveFcmTokenUsecase;
import com.noti.noti.notification.domain.model.FcmToken;
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


/**
 * FCM Token을 저장하거나 갱신하기 위한 Controller
 */
@RequiredArgsConstructor
@RestController
public class SaveFcmTokenController {

  private final SaveFcmTokenUsecase saveFcmTokenUsecase;

  @Operation(tags = "FCM Token 저장 API ", summary = "saveFcmToken", description = "FCM Token 저장",
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
      })
  @PostMapping("/api/notification/tokens")
  @Parameter(name = "userDetails", hidden = true)
  public ResponseEntity<SuccessResponse> saveFcmToken(
      @Valid @RequestBody SaveFcmTokenRequest saveFcmTokenRequest, @AuthenticationPrincipal
  UserDetails userDetails) {
    long userId = Long.parseLong(userDetails.getUsername());

    FcmToken fcmToken = saveFcmTokenUsecase.apply(
        new SaveFcmTokenCommand(saveFcmTokenRequest.getFcmToken(), userId));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(SuccessResponse.create201SuccessResponse());
  }


}
