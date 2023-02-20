package com.noti.noti.auth.adapter.out.persistence;

import com.noti.noti.auth.application.port.out.SaveRefreshTokenPort;
import com.noti.noti.auth.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefreshTokenPersistenceAdapter implements SaveRefreshTokenPort {

  private final RefreshTokenMapper refreshTokenMapper;
  private final RefreshTokenRedisRepository refreshTokenRedisRepository;

  @Override
  public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
    return refreshTokenMapper.toDomainEntity(
        refreshTokenRedisRepository.save(refreshTokenMapper.toRedisEntity(refreshToken))
    );
  }
}
