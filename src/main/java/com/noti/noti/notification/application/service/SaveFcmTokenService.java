package com.noti.noti.notification.application.service;

import com.noti.noti.notification.application.port.in.SaveFcmTokenCommand;
import com.noti.noti.notification.application.port.in.SaveFcmTokenUsecase;
import com.noti.noti.notification.application.port.out.SaveFcmTokenPort;
import com.noti.noti.notification.domain.model.FcmToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveFcmTokenService implements SaveFcmTokenUsecase {

  private final SaveFcmTokenPort saveFcmTokenPort;

  @Override
  public FcmToken apply(SaveFcmTokenCommand saveFcmTokenCommand) {
    FcmToken fcmToken = FcmToken.builder()
        .fcmToken(saveFcmTokenCommand.getFcmToken())
        .userId(saveFcmTokenCommand.getUserId())
        .deviceNum(saveFcmTokenCommand.getDeviceNum())
        .build();

    FcmToken savedFcmToken = saveFcmTokenPort.saveFcmToken(fcmToken);

    return savedFcmToken;
  }
}
