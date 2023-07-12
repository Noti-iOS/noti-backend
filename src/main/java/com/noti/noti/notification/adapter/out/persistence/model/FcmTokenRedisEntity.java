package com.noti.noti.notification.adapter.out.persistence.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(timeToLive = 60 * 60 * 24 * 60)
@Getter
public class FcmTokenRedisEntity {

  @Id
  private String deviceNum;

  @Indexed
  private Long userId;

  private String fcmToken;

  @Builder
  public FcmTokenRedisEntity(String deviceNum, Long userId, String fcmToken) {
    this.deviceNum = deviceNum;
    this.userId = userId;
    this.fcmToken = fcmToken;
  }
}
