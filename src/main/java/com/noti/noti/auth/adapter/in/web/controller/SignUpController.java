package com.noti.noti.auth.adapter.in.web.controller;

import com.noti.noti.auth.application.port.in.SignUpCommand;
import com.noti.noti.auth.application.port.in.SignUpUsecase;
import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.teacher.adpater.in.web.dto.OAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 임호준
 * @description 회원가입
 * @since 2023-02-18
 * @updated 1. CustomAuthenticationFilter에서 Forward 방식으로 해당 URL로 이동해 회원가입 진행
 */
@RestController
@RequiredArgsConstructor
public class SignUpController {

  private final SignUpUsecase signUpUsecase;


  @PostMapping("/api/signup")
  public ResponseEntity<JwtToken> signUp(@RequestAttribute OAuthInfo oAuthInfo) {
    JwtToken jwtToken = signUpUsecase.apply(SignUpCommand.builder()
        .socialId(oAuthInfo.getSocialId())
        .socialType(oAuthInfo.getSocialType())
        .email(oAuthInfo.getEmail())
        .nickname(oAuthInfo.getNickname())
        .thumbnailImageUrl(oAuthInfo.getThumbnailImageUrl())
        .build());

    return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
  }
}
