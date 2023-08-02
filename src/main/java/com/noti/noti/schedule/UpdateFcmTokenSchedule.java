package com.noti.noti.schedule;

import com.google.common.collect.Lists;
import com.noti.noti.notification.application.port.out.FindFcmTokenPort;
import com.noti.noti.notification.application.port.out.SendPushPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateFcmTokenSchedule {

  private final FindFcmTokenPort findFcmTokenPort;
  private final SendPushPort sendPushPort;

  /**
   *  매달 1일 0시 0분 0초에 실행
   *  fcmToken들의 유효성을 체크하기 위한 스케쥴링
   */
  @Scheduled(cron = "0 0 0 1 * *")
  public void updateFcmToken(){
    List<String> fcmTokenKeys = findFcmTokenPort.findAllFcmTokenKey();

    if (canSendPush(fcmTokenKeys)) {
      sendSilentMessageForVerifyToken(partitionKeys(fcmTokenKeys));
    }
  }

  /**
   * key들의 개수를 확인해 push를 보낼 수 있는지 판단하는 메서드
   * @param fcmTokenKeys
   * @return
   */
  private boolean canSendPush(List<String> fcmTokenKeys) {
    return fcmTokenKeys.size() > 0 ? true : false;
  }

  /**
   * 요청 한번 당 500개의 메세지를 요청 할 수 있어 key 목록을 500개씩 나누는 메서드
   * @param fcmTokenKeys
   * @return 분리된 key들의 리스트
   */
  private List<List<String>> partitionKeys(List<String> fcmTokenKeys) {
    return Lists.partition(fcmTokenKeys, 500);
  }

  /**
   * slient push를 보내는 메서드
   * @param partitionKeys
   */
  private void sendSilentMessageForVerifyToken(List<List<String>> partitionKeys) {
    partitionKeys.forEach(sendPushPort::sendSilentPushForVerifyToken);
  }
}
