package com.noti.noti.auth.application.port.in;

public interface ReissueTokenUsecace {

  JwtToken reissueToken(String token);

}
