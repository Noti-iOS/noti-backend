package com.noti.noti.notification.application.port.in;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateFcmTokenCommand {

  private String fcmToken;

  public UpdateFcmTokenCommand(String fcmToken) {
    this.fcmToken = fcmToken;
  }
}
