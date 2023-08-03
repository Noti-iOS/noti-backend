package com.noti.noti.notification.application.port.out;

public interface UpdateFcmTokenPort {

  void updateFcmTokenTtlById(String fcmTokenId);
}
