package com.noti.noti.teacher.adpater.in.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

public class AppleDto {

  /**
   * 1. identity token 받을 때 DTO
   * 2. public Key로 요청할 때 DTO
   * 3. public Key 생성
   * 4. Public Key로 서명 검증뒤 요청 후 받을 DTO
   */

  /**
   * 프론트에서 identity token 받음
   * 애플로 공개키 요청 -> 2개의 키 응답받음
   * identity token 헤더 디코딩(base64) -> 헤더에서 kid와 alg에 일치하는 키 선택 == 공개키
   * 공개키로 payload 디코딩
   * payload 검증 (진짜 apple에 가입했는지, 안했는지 확인하는 과정)
   *      1. JWS E256 signature 검증 jws: 서버에서 인증정보를 private key로 token화 한 것
   *      2. nonce 를 확인
   *      3. iss 값이 https://appleid.apple.com을 포함하는 지 확인
   *      4. aud 필드가 developer의 client_id인지 확인(이건 머지)
   *      5. 시간이 exp 값보다 더 이른지 확인(exp 값보다 시간이 지나면 놉)
   */
  // 애플에서 토큰을 통해 받는 선생님 정보
    @Getter @Setter
    public static class TeacherInfo {

      private String iss; // 토큰 발급 주체 : https://appleid.apple.com
      private String sub; // 사용자 고유 id
      private String aud; // apple에 등록한 client 값
      private String iat;
      private String exp;
      private String nonce;
      private String nonce_supported;
      private String email;
      private String is_private_email;
      private String real_user_status;
      private String transfer_sub;

    }


}
