package com.noti.noti.teacher.adpater.in.web.dto;

import com.noti.noti.teacher.domain.SocialType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class KakaoDto {

  /*카카오에게 받은 사용자 정보*/
  @Setter
  @Getter
  public static class TeacherInfo {

    private Long id; // 카카오 id
    private String expires_in;
    private String app_id;

    public TeacherInfo getTeacherInfo(SocialType socialType, String accessToken) {
      return WebClient.create(socialType.getUerInfoUrl())
          .get()
          .header("Authorization", "Bearer " + accessToken)
          .retrieve()
          .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(RuntimeException::new))
          .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(RuntimeException::new))
          .bodyToMono(TeacherInfo.class)
          .block();
    }
  }


  /*카카오에게 받은 토큰*/
  @Data
  public static class Token {

    private String access_token;
    private String refresh_token;

  }

  /*토큰 정보*/
  @Data
  public static class TokenInfo {

    private Long id;
    private Integer expires_in;
    private Integer app_id;

  }




}
