package com.noti.noti.auth.adapter.out.persistence;

import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "refreshToken")
public class RefreshTokenRedisEntity {
  @Id
  private String refreshToken;
  private Long id;
  private String role;

  @TimeToLive(unit = TimeUnit.MILLISECONDS)
  private Long expiration;

  @Builder
  private RefreshTokenRedisEntity(String refreshToken, Long id, String role, Long expiration) {
    this.refreshToken = refreshToken;
    this.id = id;
    this.role = role;
    this.expiration = expiration;
  }
}
