package com.noti.noti.notification.adapter.out.persistence.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 사용자의 Fcm Token 정보를 저장할 Redis Entity 입니다.
 * 문서의 요구사항에 따라 2개월의 만료기간을 설정했습니다.
 */
@RedisHash(value = "fcmToken", timeToLive = 60 * 60 * 24 * 60)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmTokenRedisEntity {
  @Id
  private String fcmToken;
  @Indexed
  private Long userId;

  @Builder
  public FcmTokenRedisEntity(Long userId, String fcmToken) {
    this.userId = userId;
    this.fcmToken = fcmToken;
  }
}
