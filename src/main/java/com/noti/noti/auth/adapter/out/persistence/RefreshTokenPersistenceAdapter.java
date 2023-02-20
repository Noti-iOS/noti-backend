package com.noti.noti.auth.adapter.out.persistence;

import com.noti.noti.auth.application.port.out.FindRefreshTokenPort;
import com.noti.noti.auth.application.port.out.SaveRefreshTokenPort;
import com.noti.noti.auth.application.port.out.DeleteRefreshTokenPort;
import com.noti.noti.auth.domain.RefreshToken;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefreshTokenPersistenceAdapter implements SaveRefreshTokenPort, FindRefreshTokenPort,
    DeleteRefreshTokenPort {

  private final RefreshTokenMapper refreshTokenMapper;
  private final RefreshTokenRedisRepository refreshTokenRedisRepository;

  @Override
  public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
    return refreshTokenMapper.toDomainEntity(
        refreshTokenRedisRepository.save(refreshTokenMapper.toRedisEntity(refreshToken))
    );
  }

  @Override
  public Optional<RefreshToken> findRefreshTokenById(String refreshToken) {
    return refreshTokenRedisRepository.findById(refreshToken)
        .map(refreshTokenMapper::toDomainEntity);
  }

  @Override
  public void deleteRefreshToken(RefreshToken refreshToken) {
    refreshTokenRedisRepository.delete(refreshTokenMapper.toRedisEntity(refreshToken));
  }
}
