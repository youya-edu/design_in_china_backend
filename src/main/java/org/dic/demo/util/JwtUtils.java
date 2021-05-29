package org.dic.demo.util;

import io.jsonwebtoken.Claims;
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
    if (!AuthenticationType.isBearer(authorizationHeader)) {
      return null;
    }
    return authorizationHeader.substring(AuthenticationType.BEARER.value().length() + 1);
  }

  public static String generateToken(String username, Date issuedAt, Claims otherClaims) {
    Claims claims = Jwts.claims();
    claims.setSubject(username);
    claims.setIssuedAt(issuedAt);
    claims.setExpiration(Date.from(issuedAt.toInstant().plusSeconds(TOKEN_EXPIRE_PERIOD)));
    if (otherClaims != null) {
      claims.putAll(otherClaims);
    }
    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  static Claims validateTokenAndExtractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  public static Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public static String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims;
    try {
      claims = validateTokenAndExtractAllClaims(token);
      return claimsResolver.apply(claims);
    } catch (JwtException e) {
      return null;
    }
  }
}
