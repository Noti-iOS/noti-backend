package com.noti.noti.common;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;


@ActiveProfiles("test")
abstract public class RedisTestContainerConfig {

  static GenericContainer<?> redis = new GenericContainer<>(
      DockerImageName.parse("redis:7.0.8-alpine"))
      .withExposedPorts(6379);

  @DynamicPropertySource
  static void redisProperties(DynamicPropertyRegistry registry) {
    redis.start();
    registry.add("spring.redis.host", redis::getHost);
    registry.add("spring.redis.port", redis::getFirstMappedPort);
  }
}
