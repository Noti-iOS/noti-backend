package com.noti.noti.auth.adapter.out.persistence;

import com.noti.noti.auth.domain.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

  RefreshTokenRedisEntity toRedisEntity(RefreshToken refreshToken){
    return RefreshTokenRedisEntity.builder()
        .id(refreshToken.getId())
        .refreshToken(refreshToken.getRefreshToken())
        .role(refreshToken.getRole())
        .expiration(refreshToken.getExpiration())
        .build();
  }

  RefreshToken toDomainEntity(RefreshTokenRedisEntity refreshTokenRedisEntity){
    return RefreshToken.builder()
        .refreshToken(refreshTokenRedisEntity.getRefreshToken())
        .id(refreshTokenRedisEntity.getId())
        .role(refreshTokenRedisEntity.getRole())
        .expiration(refreshTokenRedisEntity.getExpiration())
        .build();
  }
}
