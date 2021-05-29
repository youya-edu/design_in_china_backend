package org.dic.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JwtUtilsTest {

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
      String tamperedJwtToken =
          "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWxpY2lvdXMtdXNlcm5hbWUiLCJpYXQiOjEuNjIyMjU1NjM3RTksImV4cCI6MS42MjIzNjM2MzdFOX0.V3qG7b5GMfQzPqsp8daWJqVt7o0IHCCvRaZlj5s22g1eDda6dCncHHp-mkZw4DI-jabCV8GHpCERluCeJD2HbQ";
      assertThrows(
          SignatureException.class,
          () -> JwtUtils.validateTokenAndExtractAllClaims(tamperedJwtToken));
    }

    @Test
    @DisplayName("If JWT token is expired, throw ExpiredJwtException.")
    void ifJwtTokenIsExpired() {
      String expiredJwtToken =
          "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0LXVzZXJuYW1lIiwiaWF0IjoxNjIyMTQ3NzIxLCJleHAiOjE2MjIyNTU3MjF9.PEsY8WYLyCY-xlWNQzZrHYsWxVHKHtOAl7mB54-FQATVLSXOpbS5m8_k9f_wpWb9vmfaHBoFY1t7q35Jbz-KUg";
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
      String jwtToken =
          "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0LXVzZXJuYW1lIiwiaWF0IjoxNjIyMjYxMzQ3LCJleHAiOjE2MjIzNjkzNDcsInRlc3QtY2xhaW0iOiJ0ZXN0LXZhbHVlIn0.O93OtsqE_np1JgYv9i_QO6ihZtnQHNDVi4fWnAVS_3JjabbeI7rmLKqXLBaFT4yuf6QfUs57y_vO8QZNO1tLGg";

      String expectedClaim = "test-value";
      Object actualClaim = JwtUtils.extractClaim(jwtToken, claims -> claims.get("test-claim"));

      assertEquals(expectedClaim, actualClaim);
    }

    @Test
    @DisplayName("If JWT token does not contain the claim, return null.")
    void ifJwtTokenNotContainClaim() {
      String jwtToken =
          "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0LXVzZXJuYW1lIiwiaWF0IjoxNjIyMjYxMTQ3LCJleHAiOjE2MjIzNjkxNDd9.3K4um20aCabzqOajSxmaOGODyciOMC_qBVJT3g7n65KIGqZJI22rU4E_B68kjDxm3xPe1g6qyxRk4Mojv4XaNQ";

      Object actualClaim = JwtUtils.extractClaim(jwtToken, claims -> claims.get("test-claim"));

      assertNull(actualClaim);
    }

    @Test
    @DisplayName("If JWT token is invalid, return null.")
    void ifJwtTokenIsInvalid() {
      String invalidToken =
          "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWxpY2lvdXMtdXNlcm5hbWUiLCJpYXQiOjEuNjIyMjU1NjM3RTksImV4cCI6MS42MjIzNjM2MzdFOX0.V3qG7b5GMfQzPqsp8daWJqVt7o0IHCCvRaZlj5s22g1eDda6dCncHHp-mkZw4DI-jabCV8GHpCERluCeJD2HbQ";

      Object actualClaim = JwtUtils.extractClaim(invalidToken, claims -> claims.get("test-claim"));

      assertNull(actualClaim);
    }
  }

  @Nested
  class ExtractUsername {

    @Test
    @DisplayName("If JWT token is valid and contains username claim, return extracted username.")
    void ifJwtTokenIsValidAndContainsUsername() {
      String jwtToken =
          "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0LXVzZXJuYW1lIiwiaWF0IjoxNjIyMjYwMzgzLCJleHAiOjE2MjIzNjgzODN9.aw1Gsy7dm9N99LuFYN5lVmlrFrjortxMEcozdj44jgkaj9s63UGL9wan2KmQNRTl7Rw2llN2KBhcLfCe_qCDSw";

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
    void ifJwtTokenIsValidAndContainsExpiration() throws ParseException {
      String jwtToken =
          "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0LXVzZXJuYW1lIiwiaWF0IjoxNjIyMzM1OTgzLCJleHAiOjE2MjI0NDM5ODN9.ZiU4DMhzNOUdVVgVG99jWXB9tBgPr--S6JqEEI1iif57U5R9H4raRj7_ixxi1HvkK4IDzUXl6th1QTtD23dovA";

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      long expectedExpiration =
          simpleDateFormat
              .parse("2021-05-30T09:53:03Z")
              .toInstant()
              .plusSeconds(JwtUtils.TOKEN_EXPIRE_PERIOD)
              .getEpochSecond();
      long actualExpiration = JwtUtils.extractExpiration(jwtToken).toInstant().getEpochSecond();

      assertEquals(expectedExpiration, actualExpiration);
    }
  }
}
