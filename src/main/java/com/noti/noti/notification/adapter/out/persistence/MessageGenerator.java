package com.noti.noti.notification.adapter.out.persistence;

import static com.noti.noti.notification.adapter.out.persistence.MessageHeader.*;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.MulticastMessage;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * fcm에 보낼 Message를 생성하는 클래스
 */
@Component
public class MessageGenerator {

  /**
   * silent message를 생성하는 메서드
   *
   * @param fcmTokens 메세지를 보낼 타겟 tokens
   * @return 보내는 토큰들이 담겨있는 MulticastMessage
   */
  public MulticastMessage generateMulticastMessageForSilent(List<String> fcmTokens) {
    return MulticastMessage.builder().addAllTokens(fcmTokens)
        .setApnsConfig(generateApnsConfigForSilent())
        .build();
  }

  /**
   * silent message를 위한 apns config 생성 메서드
   *
   * @return silent push apns config
   */
  private ApnsConfig generateApnsConfigForSilent() {
    return ApnsConfig.builder()
        .setAps(Aps.builder().setContentAvailable(true).setCategory("update-fcmToken").build())
        .putHeader(
            APNS_PUSH_TYPE.getHeader(), "background")
        .putHeader(APNS_PRIORITY.getHeader(), "5").build();
  }
}
