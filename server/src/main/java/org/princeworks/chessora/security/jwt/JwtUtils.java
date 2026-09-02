package org.princeworks.chessora.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.princeworks.chessora.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
  @Value("${spring.jwt.secret}")
  private String jwtSecret;

  @Value("${spring.jwt.expirationMs}")
  private int jwtExpirationMs;
  
  @Value("${spring.jwt.cookie-name}")
  private String jwtCookie;
  
  @Value("${spring.jwt.cookie-secure}")
  private Boolean cookieSecure;

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }
  
  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null)
      return cookie.getValue();
    return null;
  }
  
  public ResponseCookie generateJwtCookie(UserDetailsImpl userDetails) {
    String jwt = generateTokenFromUserName(userDetails);
    return ResponseCookie.from(jwtCookie, jwt)
            .path("/api")
            .maxAge(Duration.ofDays(1))
            .httpOnly(true)
            .secure(cookieSecure)
            .sameSite("Strict")
            .build();
  }
  
  public ResponseCookie getCleanJwtCookie() {
    return ResponseCookie.from(jwtCookie, null)
            .path("/api")
            .httpOnly(true)
            .secure(cookieSecure)
            .sameSite("Strict")
            .maxAge(0)
            .build();
  }

  public String generateTokenFromUserName(UserDetails details) {
    String username = details.getUsername();
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(key())
        .compact();
  }

  public String getUserNameFromJwt(String token) {
    return Jwts.parser()
        .verifyWith((SecretKey) key())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  public Boolean validateJwt(String token) {
    try {
      Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT Token : {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT Token Expired : {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT Token is Unsupported : {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty : {}", e.getMessage());
    }
    return false;
  }
}
