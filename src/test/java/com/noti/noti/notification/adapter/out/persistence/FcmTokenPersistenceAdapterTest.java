package com.noti.noti.notification.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.common.RedisTestContainerConfig;
import com.noti.noti.notification.domain.model.FcmToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataRedisTest
@ActiveProfiles("test")
@Import({FcmTokenPersistenceAdapter.class, FcmTokenMapper.class})
@DisplayName("FcmTokenPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class FcmTokenPersistenceAdapterTest extends RedisTestContainerConfig {

  @Autowired
  private FcmTokenPersistenceAdapter fcmTokenPersistenceAdapter;

  @Nested
  class saveFcmToken_메서드는 {

    @Nested
    class 새로운_FcmToken_엔티티가_주어지면 {

      @Test
      void 성공적으로_해당_객체를_저장하고_저장된_객체를_반환한다 (){
        FcmToken givenFcmToken = MonkeyUtils.MONKEY.giveMeBuilder(FcmToken.class).setNull("deviceNum").sample();
        FcmToken savedFcmToken = fcmTokenPersistenceAdapter.saveFcmToken(givenFcmToken);

        assertThat(savedFcmToken.getDeviceNum()).isNotNull();
      }
    }

    @Nested
    class 이미_존재하는_ID의_FcmToken_엔티티가_주어지면 {

      final String DEVICE_NUM = "deviceNum";

      @Test
      void 성공적으로_값을_업데이트하고_갱신된_객체를_반환한다 (){
        FcmToken savedFcmToken = fcmTokenPersistenceAdapter.saveFcmToken(
            MonkeyUtils.MONKEY.giveMeBuilder(FcmToken.class).set("deviceNum", DEVICE_NUM).sample());

        FcmToken givenFcmToken = MonkeyUtils.MONKEY.giveMeBuilder(FcmToken.class)
            .set("deviceNum", DEVICE_NUM).set("fcmToken", "updated").sample();

        FcmToken updatedFcmToken = fcmTokenPersistenceAdapter.saveFcmToken(givenFcmToken);
        assertAll(
            () -> assertThat(updatedFcmToken.getFcmToken()).isNotEqualTo(savedFcmToken.getFcmToken()),
            () -> assertThat(updatedFcmToken.getDeviceNum()).isEqualTo(savedFcmToken.getDeviceNum())
        );
      }
    }
  }

}