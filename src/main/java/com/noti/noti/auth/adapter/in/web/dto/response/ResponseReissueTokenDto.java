package com.noti.noti.auth.adapter.in.web.dto.response;

import com.noti.noti.auth.application.port.in.JwtToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseReissueTokenDto {

  @Schema(description = "엑세스 토큰", example = "access-token")
  private String accessToken;
  @Schema(description = "리프레시 토큰", example = "refresh-token")
  private String refreshToken;

  public static ResponseReissueTokenDto of(JwtToken token) {
    return new ResponseReissueTokenDto(token.getAccessToken(), token.getRefreshToken());
  }

  private ResponseReissueTokenDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
