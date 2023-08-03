package com.noti.noti.notification.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.common.RedisTestContainerConfig;
import com.noti.noti.notification.adapter.out.persistence.model.FcmTokenRedisEntity;
import com.noti.noti.notification.domain.model.FcmToken;
import java.time.Duration;
import java.util.List;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@DataRedisTest
@ActiveProfiles("test")
@Import({FcmTokenPersistenceAdapter.class, FcmTokenMapper.class})
@DisplayName("FcmTokenPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class FcmTokenPersistenceAdapterTest extends RedisTestContainerConfig {

  @Autowired
  private FcmTokenPersistenceAdapter fcmTokenPersistenceAdapter;

  @Autowired
  private FcmTokenRedisRepository fcmTokenRedisRepository;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @AfterEach
  void deleteAll() {
    fcmTokenRedisRepository.deleteAll();
  }

  @Nested
  class existsById_메서드는 {

    final String ID = "ID";
    final String KEY = "fcmToken:ID";

    @Nested
    class ID에_해당하는_Key가_존재하면 {

      @BeforeEach
      void setup() {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(KEY, "fcmToken", ID);
      }

      @Test
      void true를_반환한다() {
        boolean isExists = fcmTokenPersistenceAdapter.existsById(ID);
        assertThat(isExists).isTrue();
      }
    }

    @Nested
    class ID에_해당하는_Key가_존재하지_않으면 {

      @Test
      void false를_반환한다() {
        boolean isExists = fcmTokenPersistenceAdapter.existsById(ID);
        assertThat(isExists).isFalse();
      }
    }
  }

  @Nested
  class updateFcmTokenTtl_메서드는 {

    final String ID = "ID";
    final String KEY = "fcmToken:ID";

    @BeforeEach
    void setup() {
      HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
      hashOperations.put(KEY, "fcmToken", ID);
      stringRedisTemplate.expire(KEY, Duration.ofSeconds(30));
    }

    @Nested
    class 만료기간을_갱신할_객체가_주어지면 {

      @Test
      void 성공적으로_만료기간을_갱신한다() {
        Long expire = stringRedisTemplate.getExpire(KEY);

        fcmTokenPersistenceAdapter.updateFcmTokenTtlById(ID);

        Long updatedExpire = stringRedisTemplate.getExpire(KEY);
        assertThat(expire).isNotEqualTo(updatedExpire);
      }
    }
  }

  @Nested
  class saveFcmToken_메서드는 {

    @Nested
    class 새로운_FcmToken_엔티티가_주어지면 {

      @Test
      void 성공적으로_해당_객체를_저장하고_저장된_객체를_반환한다() {
        FcmToken givenFcmToken = MonkeyUtils.MONKEY.giveMeBuilder(FcmToken.class)
            .setNotNull("fcmToken").sample();
        FcmToken savedFcmToken = fcmTokenPersistenceAdapter.saveFcmToken(givenFcmToken);

        assertThat(savedFcmToken.getFcmToken()).isNotNull();
      }
    }

    @Nested
    class 이미_존재하는_ID의_FcmToken_엔티티가_주어지면 {

      final String FCM_TOKEN = "FCMTOKEN";

      @Test
      void 성공적으로_값을_업데이트하고_갱신된_객체를_반환한다() {
        FcmToken savedFcmToken = fcmTokenPersistenceAdapter.saveFcmToken(
            MonkeyUtils.MONKEY.giveMeBuilder(FcmToken.class).set("fcmToken", FCM_TOKEN)
                .setNotNull("userId").sample());

        FcmToken givenFcmToken = MonkeyUtils.MONKEY.giveMeBuilder(FcmToken.class)
            .set("fcmToken", FCM_TOKEN).set("userId", 1L).sample();

        FcmToken updatedFcmToken = fcmTokenPersistenceAdapter.saveFcmToken(givenFcmToken);
        assertAll(
            () -> assertThat(updatedFcmToken.getFcmToken()).isEqualTo(savedFcmToken.getFcmToken()),
            () -> assertThat(updatedFcmToken.getUserId()).isNotEqualTo(savedFcmToken.getUserId())
        );
      }
    }
  }

  @Nested
  class findAll_메서드는 {

    @Nested
    class 조회할_Token이_존재하지_않으면 {

      @Test
      void 빈_리스트를_반환한다() {
        List<String> fcmTokenKey = fcmTokenPersistenceAdapter.findAllFcmTokenKey();
        assertThat(fcmTokenKey).isEmpty();
      }
    }

    @Nested
    class 조회할_Token이_존재하면 {

      @BeforeEach
      void init() {
        List<FcmTokenRedisEntity> fcmTokenRedisEntities = MonkeyUtils.MONKEY.giveMeBuilder(
                FcmTokenRedisEntity.class).setNull("fcmToken")
            .set("userId", Arbitraries.longs().greaterOrEqual(1L))
            .minSize("userId", 1)
            .sampleList(100);

        fcmTokenRedisRepository.saveAll(fcmTokenRedisEntities);
      }

      @Test
      void fcmToken의_key_리스트를_반환한다() {
        List<String> fcmTokenKey = fcmTokenPersistenceAdapter.findAllFcmTokenKey();
        assertThat(fcmTokenKey).isNotEmpty();
      }
    }
  }


}