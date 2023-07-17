package com.noti.noti.notification.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmToken {

  private String deviceNum;

  private Long userId;

  private String fcmToken;

  @Builder
  public FcmToken(String deviceNum, Long userId, String fcmToken) {
    this.deviceNum = deviceNum;
    this.userId = userId;
    this.fcmToken = fcmToken;
  }
}
