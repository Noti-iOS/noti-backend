package com.noti.noti.common.adapter.out.listener;

import com.noti.noti.common.RedisTestContainerConfig;
import com.noti.noti.config.RedisTemplateTestConfig;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({RedisTemplateTestConfig.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DisplayName("ExpirationListenerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ExpirationListenerTest extends RedisTestContainerConfig {
  @Autowired
  StringRedisTemplate stringRedisTemplate;

  final String KEY = "1";
  @Test
  void test() throws InterruptedException {
    ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
    stringStringValueOperations.set(KEY, "1");
    stringStringValueOperations.set("4242", "1");
    stringRedisTemplate.expireAt(KEY, Timestamp.valueOf(LocalDateTime.now().plusSeconds(1L)));
    Thread.sleep(1100);
  }
}