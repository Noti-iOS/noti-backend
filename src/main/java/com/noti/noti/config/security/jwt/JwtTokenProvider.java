package com.noti.noti.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * - accessToken 발급, 검증, 갱신 - refreshToken 발급, 검증, 갱신
 */

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final UserDetailsService userDetailsService;
  @Value("${jwt.secret}")
  private String SECRET_KEY;


  /* String 타입의 SECRET_KEY Key타입으로 변환*/
  public Key getSigningKey(String secretKey) {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }


  /* accessToken 발급 */
  public String createAccessToken(Authentication authentication) {

    Long expiredTime = 1000L * 60 * 60 * 24 *7;

    Date now = new Date();
    return Jwts.builder()
        // header
        .setHeaderParam("typ", "ACCESS_TOKEN").setHeaderParam("alg", "HS256")
        // payload
        .setSubject(authentication.getName()).setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + expiredTime))
        .claim("role", authentication.getAuthorities())
        // signature
        .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256).compact();

  }

  /* refreshToken 발급 */
  public String createRefreshToken(Authentication authentication) {

    Date now = new Date();
    Long expiredTime = 1000L * 60 * 60 * 24 *7;
    return Jwts.builder()
        // header
        .setHeaderParam("typ", "REFRESH_TOKEN").setHeaderParam("alg", "HS256")
        // payload
        .setSubject(authentication.getName()).setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + expiredTime))
        .claim("role", authentication.getAuthorities())
        // signature
        .signWith(getSigningKey(SECRET_KEY)).compact();
  }


  /* 토큰 검증 */
  public boolean validateToken(String token) {
    Jws<Claims> jws;
    try {
      jws = Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /* 토큰 갱신 */
  public String updateAccessToken() {
    return null;
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getSubject(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getSubject(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  private Claims getClaimsFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
        .parseClaimsJws(token).getBody();
  }

}
