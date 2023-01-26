package com.noti.noti.config.security.jwt;

import com.noti.noti.common.application.port.out.JwtPort;
import com.noti.noti.error.exception.CustomExpiredJwtException;
import com.noti.noti.error.exception.CustomIllegalArgumentException;
import com.noti.noti.error.exception.CustomMalformedJwtException;
import com.noti.noti.error.exception.CustomSignatureException;
import com.noti.noti.error.exception.CustomUnsupportedJwtException;
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
public class JwtTokenProvider implements JwtPort {

  private final UserDetailsService userDetailsService;
  @Value("${jwt.secret}")
  private String SECRET_KEY;

  private final Long ACCESS_EXPIRATION_TIME = 1000L * 60 * 60 * 6;
  private final Long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 14;


  /* String 타입의 SECRET_KEY Key타입으로 변환*/
  public Key getSigningKey(String secretKey) {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }


  /* accessToken 발급 */
  public String createAccessToken(String name, String authorities) {

    Date now = new Date();
    return Jwts.builder()
        // header
        .setHeaderParam("typ", "ACCESS_TOKEN").setHeaderParam("alg", "HS256")
        // payload
        .setSubject(name).setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + ACCESS_EXPIRATION_TIME)).claim("role", authorities)
        // signature
        .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256).compact();

  }

  /* refreshToken 발급 */
  public String createRefreshToken(String name, String authorities) {

    Date now = new Date();
    return Jwts.builder()
        // header
        .setHeaderParam("typ", "REFRESH_TOKEN").setHeaderParam("alg", "HS256")
        // payload
        .setSubject(name).setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + REFRESH_EXPIRATION_TIME)).claim("role", authorities)
        // signature
        .signWith(getSigningKey(SECRET_KEY)).compact();
  }


  /* 토큰 검증 */
  public void validateToken(String token)
      throws CustomMalformedJwtException, CustomUnsupportedJwtException, CustomExpiredJwtException, CustomSignatureException, IllegalArgumentException {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build().parseClaimsJws(token);
    } catch (ExpiredJwtException e) {
      throw new CustomExpiredJwtException(e.getMessage());
    } catch (UnsupportedJwtException e) {
      throw new CustomUnsupportedJwtException(e.getMessage());
    } catch (MalformedJwtException e) {
      throw new CustomMalformedJwtException(e.getMessage());
    } catch (SignatureException e) {
      throw new CustomSignatureException(e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new CustomIllegalArgumentException(e.getMessage());
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

  public String getSubject(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  private Claims getClaimsFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
        .parseClaimsJws(token).getBody();
  }

}
