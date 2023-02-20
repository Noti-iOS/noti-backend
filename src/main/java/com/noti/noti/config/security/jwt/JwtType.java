package com.noti.noti.config.security.jwt;

import lombok.Getter;

@Getter
public enum JwtType {
  ACCESS_TOKEN(1000L * 60 * 60),
  REFRESH_TOKEN(1000L * 60 * 60 * 24 * 14);

  private Long expiration;

  JwtType(Long expiration) {
    this.expiration = expiration;
  }
}
