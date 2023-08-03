package com.noti.noti.notification.application.service;

import com.noti.noti.notification.application.port.in.UpdateFcmTokenCommand;
import com.noti.noti.notification.application.port.in.UpdateFcmTokenUsecase;
import com.noti.noti.notification.application.port.out.FindFcmTokenPort;
import com.noti.noti.notification.application.port.out.UpdateFcmTokenPort;
import com.noti.noti.notification.exception.FcmTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 토큰 만료기간 갱신 Usecase 구현
 */
@Service
@RequiredArgsConstructor
public class UpdateFcmTokenService implements UpdateFcmTokenUsecase {

  private final FindFcmTokenPort findFcmTokenPort;
  private final UpdateFcmTokenPort updateFcmTokenPort;

  @Override
  public void updateFcmToken(UpdateFcmTokenCommand updateFcmTokenCommand) {
    verifyToken(updateFcmTokenCommand.getFcmToken());
    updateFcmTokenTtl(updateFcmTokenCommand.getFcmToken());
  }

  /**
   * id에 해당하는 정보가 있는지 확인
   * @param fcmTokenId
   * @exception FcmTokenNotFoundException
   */
  private void verifyToken(String fcmTokenId) {
    if (!findFcmTokenPort.existsById(fcmTokenId)) {
      throw new FcmTokenNotFoundException();
    }
  }

  /**
   * id에 해당하는 fcmToken의 만료기간을 갱신
   * @param fcmTokenId
   */
  private void updateFcmTokenTtl(String fcmTokenId) {
    updateFcmTokenPort.updateFcmTokenTtlById(fcmTokenId);
  }
}
