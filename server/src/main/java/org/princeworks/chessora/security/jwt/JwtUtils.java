package org.princeworks.chessora.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
  @Value("${spring.jwt.secret}")
  private String jwtSecret;

  @Value("${spring.jwt.expirationMs}")
  private int jwtExpirationMs;

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getJwtFromHeader(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");
    log.debug("Authorization header : {}", bearer);

    if (bearer != null && bearer.startsWith("Bearer ")) return bearer.substring(7);

    return null;
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
