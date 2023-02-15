package com.noti.noti.auth.application.port.out;

import com.noti.noti.auth.domain.RefreshToken;

public interface SaveRefreshTokenPort {

  void saveRefreshToken(RefreshToken refreshToken);
}
