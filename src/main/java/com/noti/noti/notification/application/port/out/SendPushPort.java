package com.noti.noti.notification.application.port.out;

import java.util.List;

public interface SendPushPort {
  void sendSilentPushForVerifyToken(List<String>fcmToken);
}
