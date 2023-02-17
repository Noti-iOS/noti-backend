package com.noti.noti.config.security.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.noti.aop.ExecutionTimeChecker;
import com.noti.noti.error.exception.OauthAuthenticationException;
import com.noti.noti.teacher.adpater.in.web.dto.ApplePublicKeyResponse;
import com.noti.noti.teacher.adpater.in.web.dto.OAuthInfo;
import com.noti.noti.teacher.domain.SocialType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@ExecutionTimeChecker
public class AppleOAuthUtil implements OAuthUtil {

  // 토큰을 검증하기 위한 공개키 요청
  @Override
  public OAuthInfo getOAuthInfo(String identityToken) {

    String socialId = null;

    // 공개키 요청
    ApplePublicKeyResponse response = WebClient.create("https://appleid.apple.com/auth/keys")
        .get()
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError,
            clientResponse -> clientResponse.bodyToMono(String.class)
                .flatMap(error ->
                    Mono.error(new OauthAuthenticationException(error))))
        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(RuntimeException::new))
        .bodyToMono(ApplePublicKeyResponse.class)
        .block();

    // 토큰 헤더 부분 가져오기
    String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));

    // 헤더 디코딩하고 Map클래스에 담기
    Map<String, String> header = null;
    OAuthInfo oAuthInfo = null;

    try {
      header = new ObjectMapper().readValue(
          new String(Base64.getDecoder().decode(headerOfIdentityToken), StandardCharsets.UTF_8),
          Map.class);

      // 응답 받은 두개의 키에서 헤더의 kid와 alg가 같은 키를 key에 저장
      ApplePublicKeyResponse.Key key = response.getMatchedKeyBy(header.get("kid"),
              header.get("alg"))
          .orElseThrow(
              () -> new OauthAuthenticationException(
                  "Failed get public key from apple's id server."));

      // 선택한 키의 n값과 e값을 디코딩 -> 디코딩한 값을 사용해 공개키를 만듦
      byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
      byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

      // n과 e를 bytes에서 BigInteger 로 변환
      BigInteger n = new BigInteger(1, nBytes);
      BigInteger e = new BigInteger(1, eBytes);

      // 공개 키 생성
      RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
      KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
      PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

      // 공개키를 사용해 identity 토큰 파싱 -> id 값 가져오기

      Claims body = Jwts.parserBuilder().setSigningKey(publicKey).build()
          .parseClaimsJws(identityToken).getBody();

      oAuthInfo = OAuthInfo.builder()
          .nickname("Noti")
          .socialType(SocialType.APPLE)
          .socialId(body.get("sub", String.class))
          .email(body.get("email", String.class))
          .build();

    } catch (JsonProcessingException e) {
      throw new OauthAuthenticationException(e.getMessage());
    } catch (NoSuchAlgorithmException e) {
      throw new OauthAuthenticationException(e.getMessage());
    } catch (InvalidKeySpecException e) {
      throw new OauthAuthenticationException(e.getMessage());
    }

    return oAuthInfo;
  }

}
