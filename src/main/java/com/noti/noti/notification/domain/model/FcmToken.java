package com.noti.noti.notification.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmToken {

  private Long userId;
  private String fcmToken;

  @Builder
  public FcmToken(Long userId, String fcmToken) {
    this.userId = userId;
    this.fcmToken = fcmToken;
  }
}
