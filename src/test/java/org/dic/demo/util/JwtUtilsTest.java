package org.dic.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.util.Base64Utils;

class JwtUtilsTest {

  @ParameterizedTest
  @CsvSource({"some.jwt.token, Bearer some.jwt.token"})
  void GetToken_AuthorizationHeaderIsBearerType_TokenGot(
      String expectedJwtToken, String authorizationHeader) {
    assertEquals(expectedJwtToken, JwtUtils.getToken(authorizationHeader));
  }

  @Test
  void ValidateToken_JwtTokenOk_Valid() {
    String jwtToken = JwtUtils.generateToken("test-username");
    assertTrue(JwtUtils.validateToken(jwtToken));
  }

  @Test
  @SuppressWarnings("unchecked")
  void ValidateToken_JwtTokenTampered_Invalid() {
    String[] jwtTokenParts = JwtUtils.generateToken("test-username").split("\\.");
    String originalPayload = jwtTokenParts[1];

    Gson gson = new Gson();
    Map<String, Object> map =
        gson.fromJson(new String(Base64Utils.decodeFromString(originalPayload)), Map.class);
    // tamper username
    map.put("sub", "malicious-username");
    String tamperedPayload = gson.toJson(map);
    jwtTokenParts[1] = Base64Utils.encodeToString(tamperedPayload.getBytes()).replace("=", "");
    String tamperedJwtToken = String.join(".", jwtTokenParts);

    assertFalse(JwtUtils.validateToken(tamperedJwtToken));
  }

  @Test
  void ValidateToken_JwtTokenWithoutUsernameClaim_Invalid() {
    String jwtTokenWithoutUsernameClaim = JwtUtils.generateToken((String) null);
    assertFalse(JwtUtils.validateToken(jwtTokenWithoutUsernameClaim));
  }

  @Test
  void ValidateToken_JwtTokenExpired_Invalid() {
    String jwtTokenExpired =
        JwtUtils.generateToken(
            "test-username",
            Date.from(Instant.now().minusSeconds(JwtUtils.TOKEN_EXPIRE_PERIOD + 1)));
    assertFalse(JwtUtils.validateToken(jwtTokenExpired));
  }

  @Test
  void ExtractUsername_JwtTokenNotTampered_UsernameGot() {
    String jwtToken = JwtUtils.generateToken("test-username");
    assertEquals("test-username", JwtUtils.extractUsername(jwtToken));
  }

  @Test
  void ExtractExpiration_JwtTokenNotTampered_ExpirationGot() {
    Date now = new Date();
    String jwtToken = JwtUtils.generateToken("test-username", now);
    assertEquals(
        now.toInstant().plusSeconds(JwtUtils.TOKEN_EXPIRE_PERIOD).getEpochSecond(),
        JwtUtils.extractExpiration(jwtToken).toInstant().getEpochSecond());
  }

  @Test
  void ExtractClaim_JwtTokenNotTampered_ClaimGot() {
    Claims claims = Jwts.claims();
    claims.put("test-claim", "test-value");
    String jwtTokenWithOtherClaim = JwtUtils.generateToken(claims);
    assertEquals(
        "test-value", JwtUtils.extractClaim(jwtTokenWithOtherClaim, cs -> cs.get("test-claim")));
  }
}
