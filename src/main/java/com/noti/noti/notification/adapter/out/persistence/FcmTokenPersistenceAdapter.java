package com.noti.noti.notification.adapter.out.persistence;

import com.noti.noti.notification.adapter.out.persistence.model.FcmTokenRedisEntity;
import com.noti.noti.notification.application.port.out.SaveFcmTokenPort;
import com.noti.noti.notification.domain.model.FcmToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class FcmTokenPersistenceAdapter implements SaveFcmTokenPort {

  private final FcmTokenMapper fcmTokenMapper;
  private final FcmTokenRedisRepository fcmTokenRedisRepository;

  @Override
  public FcmToken saveFcmToken(FcmToken fcmToken) {
    FcmTokenRedisEntity savedFcmTokenRedisEntity = fcmTokenRedisRepository.save(
        fcmTokenMapper.mapToRedisEntity(fcmToken));
    return fcmTokenMapper.mapToDomainEntity(savedFcmTokenRedisEntity);
  }
}
