package com.noti.noti.notification.application.port.out;

import com.noti.noti.notification.domain.model.FcmToken;

public interface SaveFcmTokenPort {

  FcmToken saveFcmToken(FcmToken fcmToken);
}
