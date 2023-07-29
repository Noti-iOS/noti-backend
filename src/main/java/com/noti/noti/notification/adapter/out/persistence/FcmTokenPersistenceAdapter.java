package com.noti.noti.notification.adapter.out.persistence;

import com.noti.noti.notification.adapter.out.persistence.model.FcmTokenRedisEntity;
import com.noti.noti.notification.application.port.out.FindFcmTokenPort;
import com.noti.noti.notification.application.port.out.SaveFcmTokenPort;
import com.noti.noti.notification.domain.model.FcmToken;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class FcmTokenPersistenceAdapter implements SaveFcmTokenPort, FindFcmTokenPort {

  private final FcmTokenMapper fcmTokenMapper;
  private final FcmTokenRedisRepository fcmTokenRedisRepository;
  private final StringRedisTemplate redisTemplate;
  private static final String FCM_TOKEN_KEY_SET_PATTERN = "fcmToken";


  @Override
  public FcmToken saveFcmToken(FcmToken fcmToken) {
    FcmTokenRedisEntity savedFcmTokenRedisEntity = fcmTokenRedisRepository.save(
        fcmTokenMapper.mapToRedisEntity(fcmToken));
    return fcmTokenMapper.mapToDomainEntity(savedFcmTokenRedisEntity);
  }

  /**
   * redis에 저장되어있는 모든 fcmToken의 key를 조회하는 메서드 findAll() 대신 scan을 사용
   *
   * @return 키의 목록을 반환
   */
  @Override
  public List<String> findAllFcmTokenKey() {
    // 10개의 단위로 키의 목록을 조회
    ScanOptions scanOptions = ScanOptions.scanOptions().count(10).build();
    Cursor<String> cursor = redisTemplate.opsForSet().scan(FCM_TOKEN_KEY_SET_PATTERN, scanOptions);

    ArrayList<String> fcmTokenKeys = new ArrayList<>();

    while (cursor.hasNext()) {
      fcmTokenKeys.add(cursor.next());
    }

    return fcmTokenKeys;
  }
}
