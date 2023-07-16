package com.noti.noti.notification.application.port.in;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveFcmTokenCommand {

  private String fcmToken;
  private String deviceNum;
  private Long userId;

  public SaveFcmTokenCommand(String fcmToken, String deviceNum, Long userId) {
    this.fcmToken = fcmToken;
    this.deviceNum = deviceNum;
    this.userId = userId;
  }
}
