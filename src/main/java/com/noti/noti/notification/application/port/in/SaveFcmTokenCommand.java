package com.noti.noti.notification.application.port.in;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveFcmTokenCommand {

  private String fcmToken;
  private Long userId;

  public SaveFcmTokenCommand(String fcmToken, Long userId) {
    this.fcmToken = fcmToken;
    this.userId = userId;
  }
}
