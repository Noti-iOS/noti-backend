package com.noti.noti.notification.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.notification.adapter.out.persistence.model.FcmTokenRedisEntity;
import com.noti.noti.notification.domain.model.FcmToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("FcmTokenMapper 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class FcmTokenMapperTest {

  private FcmTokenMapper fcmTokenMapper = new FcmTokenMapper();

  @Nested
  class mapToDomainEntity_메소드는 {

    @Nested
    class FcmTokenRedisEntity_객체가_주어지면 {

      @Test
      void Lesson_Domain_객체로_변환하고_변환된_객체를_반환한다() {
        FcmTokenRedisEntity givenFcmTokenRedisEntity = MonkeyUtils.MONKEY.giveMeOne(
            FcmTokenRedisEntity.class);

        FcmToken mappedDomainEntity = fcmTokenMapper.mapToDomainEntity(givenFcmTokenRedisEntity);
        assertThat(mappedDomainEntity.getUserId()).isEqualTo(givenFcmTokenRedisEntity.getUserId());
      }
    }
  }

  @Nested
  class mapToRedisEntity_메소드는 {

    @Nested
    class FcmToken_Domain_객체가_주어지면 {

      @Test
      void Lesson_Jpa_Entity_객체로_변환하고_변환된_객체를_반환한다() {
        FcmToken givenFcmTokenEntity = MonkeyUtils.MONKEY.giveMeOne(FcmToken.class);
        FcmTokenRedisEntity mappedRedisEntity = fcmTokenMapper.mapToRedisEntity(
            givenFcmTokenEntity);
        assertThat(mappedRedisEntity.getUserId()).isEqualTo(givenFcmTokenEntity.getUserId());
      }
    }
  }

}