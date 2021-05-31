package org.dic.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

class JwtUtilsTest {

  // This test will fail if we change the secret.
  @Test
  @DisplayName("Can generate JWT Token.")
  void generateToken() throws ParseException {
    String expectedJwtToken =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0LXVzZXJuYW1lIiwiaWF0IjoxNjIyMzM1OTgzLCJleHAiOjE2MjI0NDM5ODN9.ZiU4DMhzNOUdVVgVG99jWXB9tBgPr--S6JqEEI1iif57U5R9H4raRj7_ixxi1HvkK4IDzUXl6th1QTtD23dovA";

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    Date issueAt = simpleDateFormat.parse("2021-05-30T09:53:03Z");
    String actualJwtToken = JwtUtils.generateToken("test-username", issueAt, null);

    assertEquals(expectedJwtToken, actualJwtToken);
  }

  @Nested
  class GetToken {

    @Test
    @DisplayName("If authorization header is Bearer type, JWT token will be returned.")
    void ifAuthorizationHeaderIsBearerType() {
      String expectedJwtToken = "some.jwt.token";
      String actualJwtToken = JwtUtils.getToken("Bearer some.jwt.token");
      assertEquals(expectedJwtToken, actualJwtToken);
    }

    @Test
    @DisplayName("If authorization header is not Bearer type, null will be returned.")
    void ifAuthorizationHeaderIsNotBearerType() {
      String actualJwtToken = JwtUtils.getToken("Basic some.jwt.token");
      assertNull(actualJwtToken);
    }

    @Test
    @DisplayName("If authorization header is null, null will be returned.")
    void ifAuthorizationHeaderIsNull() {
      String actualJwtToken = JwtUtils.getToken(null);
      assertNull(actualJwtToken);
    }
  }

  @Nested
  class ValidateTokenAndExtractAllClaims {

    @Test
    @DisplayName("If JWT token is tampered, throw SignatureException.")
    void ifJwtTokenIsTampered() {

      // generate a valid JWT token
      String[] jwtTokenParts =
          JwtUtils.generateToken("test-username", new Date(), null).split("\\.");
      String originalPayload = jwtTokenParts[1];

      // tamper username
      Gson gson = new Gson();
      Map<String, Object> map =
          gson.fromJson(new String(Base64Utils.decodeFromString(originalPayload)), Map.class);
      map.put("sub", "malicious-username");
      String tamperedPayload = gson.toJson(map);
      jwtTokenParts[1] = Base64Utils.encodeToString(tamperedPayload.getBytes()).replace("=", "");
      String tamperedJwtToken = String.join(".", jwtTokenParts);

      assertThrows(
          SignatureException.class,
          () -> JwtUtils.validateTokenAndExtractAllClaims(tamperedJwtToken));
    }

    @Test
    @DisplayName("If JWT token is expired, throw ExpiredJwtException.")
    void ifJwtTokenIsExpired() throws ParseException {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      Date issueAtLongLongAgo = simpleDateFormat.parse("1990-11-01T12:34:42Z");
      Claims claims = Jwts.claims();
      claims.put("test-claim", "test-val");
      String expiredJwtToken = JwtUtils.generateToken("test-username", issueAtLongLongAgo, claims);

      assertThrows(
          ExpiredJwtException.class,
          () -> JwtUtils.validateTokenAndExtractAllClaims(expiredJwtToken));
    }
  }

  @Nested
  class ExtractClaim {

    @Test
    @DisplayName("If JWT token is valid, can extract claim.")
    void ifJwtTokenIsValid() {
      Claims claims = Jwts.claims();
      claims.put("test-claim", "test-value");
      String jwtToken = JwtUtils.generateToken("test-username", new Date(), claims);

      String expectedClaim = "test-value";
      Object actualClaim = JwtUtils.extractClaim(jwtToken, cs -> cs.get("test-claim"));

      assertEquals(expectedClaim, actualClaim);
    }

    @Test
    @DisplayName("If JWT token does not contain the claim, return null.")
    void ifJwtTokenNotContainClaim() {

      String invalidToken = JwtUtils.generateToken("test-username", new Date(), null);
      Object actualClaim = JwtUtils.extractClaim(invalidToken, cs -> cs.get("test-claim"));

      assertNull(actualClaim);
    }

    @Test
    @DisplayName("If JWT token is invalid, return null.")
    void ifJwtTokenIsInvalid() throws ParseException {

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      Date issueAtLongLongAgo = simpleDateFormat.parse("1990-11-01T12:34:42Z");
      Claims claims = Jwts.claims();
      claims.put("test-claim", "test-value");
      String invalidToken = JwtUtils.generateToken("test-username", issueAtLongLongAgo, claims);

      Object actualClaim = JwtUtils.extractClaim(invalidToken, cs -> cs.get("test-claim"));

      assertNull(actualClaim);
    }
  }

  @Nested
  class ExtractUsername {

    @Test
    @DisplayName("If JWT token is valid and contains username claim, return extracted username.")
    void ifJwtTokenIsValidAndContainsUsername() {
      String jwtToken = JwtUtils.generateToken("test-username", new Date(), null);

      String expectedUsername = "test-username";
      String actualUsername = JwtUtils.extractUsername(jwtToken);

      assertEquals(expectedUsername, actualUsername);
    }
  }

  @Nested
  class ExtractExpiration {

    @Test
    @DisplayName(
        "If JWT token is valid and contains expiration claim, return extracted expiration.")
    void ifJwtTokenIsValidAndContainsExpiration() {
      Date now = new Date();
      String jwtToken = JwtUtils.generateToken("test-username", now, null);

      long expectedExpiration =
          now.toInstant().plusSeconds(JwtUtils.TOKEN_EXPIRE_PERIOD).getEpochSecond();
      long actualExpiration = JwtUtils.extractExpiration(jwtToken).toInstant().getEpochSecond();

      assertEquals(expectedExpiration, actualExpiration);
    }
  }
}
