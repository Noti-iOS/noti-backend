package com.noti.noti.notification.adapter.out.persistence;

import com.noti.noti.notification.adapter.out.persistence.model.FcmTokenRedisEntity;
import com.noti.noti.notification.domain.model.FcmToken;
import org.springframework.stereotype.Component;


/**
 * FcmToken 도메인 엔티티와 persistence 엔티티 매핑을 위한 매퍼 클래스입니다.
 */
@Component
public class FcmTokenMapper {

  /**
   * FcmTokenRedisEntity를 FcmToken domain entity로 매핑하는 메서드
   *
   * @param fcmTokenRedisEntity 매핑할 인스턴스
   * @return 매핑된 FcmToken domain Entity
   */

  public FcmToken mapToDomainEntity(FcmTokenRedisEntity fcmTokenRedisEntity) {
    return FcmToken.builder()
        .fcmToken(fcmTokenRedisEntity.getFcmToken())
        .deviceNum(fcmTokenRedisEntity.getDeviceNum())
        .userId(fcmTokenRedisEntity.getUserId())
        .build();
  }

  /**
   * FcmTokenDomainEntity를 FcmTokenRedisEntity로 매핑하는 메서드
   *
   * @param fcmToken 매핑할 FcmToken 인스턴스
   * @return 매핑된 FcmTokenRedisEntity.
   */
  public FcmTokenRedisEntity mapToRedisEntity(FcmToken fcmToken) {
    return FcmTokenRedisEntity.builder()
        .fcmToken(fcmToken.getFcmToken())
        .deviceNum(fcmToken.getDeviceNum())
        .userId(fcmToken.getUserId())
        .build();
  }
}
