package org.dic.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.dic.demo.security.AuthenticationType;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtils {

  public static final String SECRET_KEY = "dic_secret";
  public static final long TOKEN_EXPIRE_PERIOD = 30 * 60 * 60 * 1000L;

  public static String getToken(String authorizationHeader) {
    return authorizationHeader.substring(AuthenticationType.BEARER.value().length() + 1);
  }

  public static String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  private static String createToken(Map<String, Object> claims, String subject) {
    long currentTimeInMillis = System.currentTimeMillis();
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(currentTimeInMillis))
        .setExpiration(new Date(currentTimeInMillis + TOKEN_EXPIRE_PERIOD))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public static boolean validateToken(String token, UserDetails userDetails) {
    return !isTokenExpired(token)
        && StringUtils.equals(extractUsername(token), userDetails.getUsername());
  }

  private static boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public static String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public static Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private static Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }
}
