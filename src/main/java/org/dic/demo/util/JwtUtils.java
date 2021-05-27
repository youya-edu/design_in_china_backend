package org.dic.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;
import org.dic.demo.security.AuthenticationType;

public class JwtUtils {

  public static final String SECRET_KEY = "dic_secret";
  public static final long TOKEN_EXPIRE_PERIOD = 30 * 60 * 60L;

  private JwtUtils() {}

  public static String getToken(String authorizationHeader) {
    return authorizationHeader.substring(AuthenticationType.BEARER.value().length() + 1);
  }

  public static String generateToken(String username) {
    return generateToken(username, new Date());
  }

  public static String generateToken(String username, Date issuedAt) {
    Claims claims = Jwts.claims();
    claims.setSubject(username);
    claims.setIssuedAt(issuedAt);
    claims.setExpiration(Date.from(issuedAt.toInstant().plusSeconds(TOKEN_EXPIRE_PERIOD)));
    return generateToken(claims);
  }

  static String generateToken(Claims claims) {
    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  /**
   * If the following 3 conditions are met, the token is valid.
   *
   * <ol>
   *   <li>Signature check passed
   *   <li>Not expired yet
   *   <li>Has an username claim
   * </ol>
   *
   * @param token the JWT token
   * @return true if all 3 conditions meet, else false
   */
  public static boolean validateToken(String token) {
    try {
      return extractUsername(extractAllClaims(token)) != null;
    } catch (JwtException e) {
      return false;
    }
  }

  public static Date extractExpiration(String token) {
    return extractClaim(token, JwtUtils::extractExpiration);
  }

  private static Date extractExpiration(Claims claims) {
    return claims.getExpiration();
  }

  public static String extractUsername(String token) {
    return extractClaim(token, JwtUtils::extractUsername);
  }

  private static String extractUsername(Claims claims) {
    return claims.getSubject();
  }

  public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private static Claims extractAllClaims(String token) {
    return parseToken(token).getBody();
  }

  private static Jws<Claims> parseToken(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
  }
}
