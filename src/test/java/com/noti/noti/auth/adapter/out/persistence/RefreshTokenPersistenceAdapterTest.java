package com.noti.noti.auth.adapter.out.persistence;


import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.auth.domain.RefreshToken;
import com.noti.noti.common.RedisTestContainerConfig;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;

@DataRedisTest
@Import({RefreshTokenPersistenceAdapter.class, RefreshTokenMapper.class})
@DisplayName("RefreshTokenPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Slf4j
class RefreshTokenPersistenceAdapterTest extends RedisTestContainerConfig {

  final String REFRESH_TOKEN = "refreshToken";
  @Autowired
  RefreshTokenPersistenceAdapter refreshTokenPersistenceAdapter;

  @Autowired
  RefreshTokenRedisRepository refreshTokenRedisRepository;

  @Nested
  class saveRefreshToken_몌서드는 {

    @Nested
    class 유효한_RefreshToken_객체가_주어지면 {

      @Test
      void 성공적으로_객체를_저장한다() throws InterruptedException {
        refreshTokenPersistenceAdapter.saveRefreshToken(createRefreshToken(1000L));

        Optional<RefreshTokenRedisEntity> savedRefreshToken =
            refreshTokenRedisRepository.findById(REFRESH_TOKEN);

        assertThat(savedRefreshToken).hasValueSatisfying(
            token -> assertThat(token.getRefreshToken()).isEqualTo(REFRESH_TOKEN));

        Thread.sleep(1500L);

        Optional<RefreshTokenRedisEntity> deletedRefreshToken =
            refreshTokenRedisRepository.findById(REFRESH_TOKEN);

        assertThat(deletedRefreshToken).isNotPresent();
      }
    }
  }

  RefreshToken createRefreshToken(long expiration) {
    return RefreshToken.builder()
        .refreshToken(REFRESH_TOKEN)
        .id(1L)
        .role("ROLE_TEACHER")
        .expiration(expiration)
        .build();
  }
}