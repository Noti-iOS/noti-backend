package com.noti.noti.notification.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateFcmTokenRequest {

  @NotBlank
  @Schema(description = "FCM Token", required = true, example = "fcmToken123123")
  private String fcmToken;
}
