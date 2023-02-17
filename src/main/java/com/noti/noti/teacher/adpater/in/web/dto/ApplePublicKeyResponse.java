package com.noti.noti.teacher.adpater.in.web.dto;

import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

/**
 * public key 를 통해서 받은 두개의 key값에서 jwt 검증에 필요한 key값을 찾음
 */
@Getter
@Setter
public class ApplePublicKeyResponse {

  private List<Key> keys; // 외부에서 받은 공개키

  @Getter
  @Setter
  public static class Key {

    private String kty;
    private String kid;
    private String use;
    private String alg;
    private String n;
    private String e;
  }

  public Optional<ApplePublicKeyResponse.Key> getMatchedKeyBy(String kid, String alg) {
    return this.keys.stream()
        .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
        .findFirst();
  }
}
