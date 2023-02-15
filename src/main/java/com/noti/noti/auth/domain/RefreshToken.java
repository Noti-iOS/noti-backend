package com.noti.noti.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshToken {
  private String refreshToken;
  private Long id;
  private String role;
  private Long expiration;

  @Builder
  private RefreshToken(String refreshToken, Long id, String role, Long expiration) {
    this.refreshToken = refreshToken;
    this.id = id;
    this.role = role;
    this.expiration = expiration;
  }
}
