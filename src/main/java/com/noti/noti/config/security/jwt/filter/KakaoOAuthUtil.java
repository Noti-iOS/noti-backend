package com.noti.noti.config.security.jwt.filter;

import com.noti.noti.error.exception.OauthAuthenticationException;
import com.noti.noti.teacher.adpater.in.web.dto.KakaoOAuthInfo;
import com.noti.noti.teacher.adpater.in.web.dto.OAuthInfo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class KakaoOAuthUtil implements OAuthUtil {

  @Override
  public OAuthInfo getOAuthInfo(String token) throws OauthAuthenticationException{
    KakaoOAuthInfo kakaoOAuthInfo = WebClient.create("https://kapi.kakao.com/v2/user/me")
        .get()
        .header("Authorization", "Bearer " + token)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse.bodyToMono(String.class)
            .flatMap(error ->
                Mono.error(new OauthAuthenticationException(error))))
        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(RuntimeException::new))
        .bodyToMono(KakaoOAuthInfo.class)
        .block();

    System.out.println(kakaoOAuthInfo);
    return OAuthInfo.builder().socialId(kakaoOAuthInfo.getId())
        .nickname(kakaoOAuthInfo.getKakaoAccount().getProfile().getNickname())
        .thumbnailImageUrl(kakaoOAuthInfo.getKakaoAccount().getProfile().getThumbnailImageUrl()).build();
  }
}
