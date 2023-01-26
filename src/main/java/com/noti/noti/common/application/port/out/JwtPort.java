package com.noti.noti.common.application.port.out;

public interface JwtPort {
  String createAccessToken(String name, String authorities);
  String createRefreshToken(String name, String authorities);
  void validateToken(String token);

  String getSubject(String token);
}
