package com.noti.noti.notification.adapter.out.persistence;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.noti.noti.notification.application.port.out.SendPushPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * push 메세지를 보내는 FCM adapter 클래스
 *
 */
@Component
@RequiredArgsConstructor
public class FcmAdapter implements SendPushPort {

  private final MessageGenerator messageGenerator;

  /**
   * silent push를 비동기 방식으로 보내는 메서드
   * @param fcmToken fcm 토큰 목록
   */
  @Override
  public void sendSilentPushForVerifyToken(List<String> fcmToken) {
    MulticastMessage multicastMessage = messageGenerator.generateMulticastMessageForSilent(
        fcmToken);

    FirebaseMessaging.getInstance().sendEachForMulticastAsync(multicastMessage);
  }

}
