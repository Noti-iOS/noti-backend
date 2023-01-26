package com.noti.noti.auth.application.port.in;

import com.noti.noti.auth.domain.JwtToken;

public interface ReissueTokenUsecace {

  JwtToken reissueToken(String token);

}
