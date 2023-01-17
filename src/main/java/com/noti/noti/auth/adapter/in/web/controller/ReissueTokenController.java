package com.noti.noti.auth.adapter.in.web.controller;

import com.noti.noti.auth.adapter.in.web.dto.response.ResponseReissueTokenDto;
import com.noti.noti.auth.application.port.in.JwtToken;
import com.noti.noti.auth.application.port.in.ReissueTokenUsecace;
import com.noti.noti.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueTokenController {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";

  private final ReissueTokenUsecace reissueTokenUsecace;

  @Operation(tags = "토큰 갱신 API", summary = "ReissueToken", description = "토큰이 만료됐을 시 갱신하는 API.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성공",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ResponseReissueTokenDto.class))}),
      @ApiResponse(responseCode = "500", description = "서버에러", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "401", description = "인증관련 에러", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "404", description = "리소스가 존재하지 않습니다", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @GetMapping("/api/auth/reissue")
  @Parameter(name = "Authorization", description = "refresh token")
  public ResponseEntity reissueToken(@RequestHeader(AUTHORIZATION_HEADER) String token) {
    JwtToken jwtToken = reissueTokenUsecace.reissueToken(resolveToken(token));

    return ResponseEntity.ok(ResponseReissueTokenDto.of(jwtToken));
  }

  private String resolveToken(String token) {
    if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
      return token.substring(7);
    }
    return null;
  }
}
