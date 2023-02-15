package com.noti.noti.config.security.jwt;

import lombok.Getter;

@Getter
public enum TokenExpiration {
  ACCESS_TOKEN(1000L * 60 * 60),
  REFRESH_TOKEN(1000L * 60 * 60 * 24 * 14);

  private Long expiration;

  TokenExpiration(Long expiration) {
    this.expiration = expiration;
  }
}
