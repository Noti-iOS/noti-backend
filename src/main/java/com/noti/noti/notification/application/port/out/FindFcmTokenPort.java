package com.noti.noti.notification.application.port.out;

import java.util.List;

public interface FindFcmTokenPort {

  List<String> findAllFcmTokenKey();
  boolean existsById(String fcmTokenId);
}
