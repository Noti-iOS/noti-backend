package com.noti.noti.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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

  private final Long ACCESS_EXPIRATION_TIME = 1000L * 60 * 60 * 24;
  private final Long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 14;


  /* String 타입의 SECRET_KEY Key타입으로 변환*/
  public Key getSigningKey(String secretKey) {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }


  /* accessToken 발급 */
  public String createAccessToken(Authentication authentication) {

    Date now = new Date();
    return Jwts.builder()
        // header
        .setHeaderParam("typ", "ACCESS_TOKEN").setHeaderParam("alg", "HS256")
        // payload
        .setSubject(authentication.getName()).setIssuedAt(now)
        .setExpiration(new Date(now.getTime()+ ACCESS_EXPIRATION_TIME))
        .claim("role", authentication.getAuthorities())
        // signature
        .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256).compact();

  }

  /* refreshToken 발급 */
  public String createRefreshToken(Authentication authentication) {

    Date now = new Date();
    return Jwts.builder()
        // header
        .setHeaderParam("typ", "REFRESH_TOKEN").setHeaderParam("alg", "HS256")
        // payload
        .setSubject(authentication.getName()).setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + REFRESH_EXPIRATION_TIME))
        .claim("role", authentication.getAuthorities())
        // signature
        .signWith(getSigningKey(SECRET_KEY)).compact();
  }


  /* 토큰 검증 */
  public boolean validateToken(String token)
      throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      throw e;
    }
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
