package com.jobFinder.be.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.jobFinder.be.exception.InvalidTokenException;
import com.jobFinder.be.service.AuthUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final AuthUserDetailsService authUserDetailsService;

  @Value("${jwt.secret}")
  private String secretkey;

  @Value("${jwt.expiration}")
  private Long expiration;

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretkey.getBytes());
  }

  private Claims getClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String getUsername(String token) {
    return getClaims(token).getSubject();
  }

  public String extractRole(String token) {
    return getClaims(token).get("role", String.class);
  }

  public String generateToken(UserDetails userDetails, String role) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", role);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    try {
      Claims claims = getClaims(token);
      String username = claims.getSubject();
      Date expiration = claims.getExpiration();

      return username.equals(userDetails.getUsername()) && expiration.after(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  public UserDetails validateAndCheckToken(String token) {
    if (token == null || token.isBlank()) {
      throw new InvalidTokenException("Token is missing");
    }

    String username = getUsername(token);
    UserDetails userDetails = authUserDetailsService.loadUserByUsername(username);

    if (!validateToken(token, userDetails)) {
      throw new InvalidTokenException("Token is invalid or expired");
    }

    return userDetails;
  }
}
