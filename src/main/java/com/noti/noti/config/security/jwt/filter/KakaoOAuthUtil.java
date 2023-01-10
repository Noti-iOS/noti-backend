package com.noti.noti.config.security.jwt.filter;

import com.noti.noti.teacher.adpater.in.web.dto.KakaoDto.TeacherInfo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("kakao")
public class KakaoOAuthUtil implements OAuthUtil {

  @Override
  public String getSocialIdBy(String token) {
    TeacherInfo teacherInfo = WebClient.create("https://kapi.kakao.com/v2/user/me")
        .get()
        .header("Authorization", "Bearer " + token)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(RuntimeException::new))
        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(RuntimeException::new))
        .bodyToMono(TeacherInfo.class)
        .block();

    return teacherInfo.getId().toString();
  }
}
