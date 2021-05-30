package org.dic.demo.user.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Date;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
import org.junit.jupiter.api.Test;

public class DatabaseUserTest {

  @Test
  void toDomainUser() {
    DatabaseUser databaseUser =
        DatabaseUser.builder()
            .accountExpired(false)
            .accountLocked(false)
            .avatar("avatar")
            .createdAt(Date.from(Instant.ofEpochMilli(1621905378)))
            .credentialsExpired(false)
            .email("email")
            .description("description")
            .enabled(true)
            .username("username")
            .nickname("nickname")
            .password("password")
            .phone("phone")
            .build();
    User user = databaseUser.toDomainObject();
    assertTrue(user.isAccountNonExpired());
    assertTrue(user.isAccountNonLocked());
    assertEquals("avatar", user.getAvatar());
    assertTrue(user.isCredentialsNonExpired());
    assertEquals("email", user.getEmail());
    assertEquals("description", user.getDescription());
    assertTrue(user.isEnabled());
    assertEquals("username", user.getUsername());
    assertEquals("nickname", user.getNickname());
    assertEquals("password", user.getPassword());
    assertEquals("phone", user.getPhone());
    assertEquals(Date.from(Instant.ofEpochMilli(1621905378)), user.getCreatedAt());

    assertTrue(
        user.getAuthorities().isEmpty(),
        "Default databaseUser to user should have empty authorities.");
    assertTrue(
        user.getCompositionCollection().getCompositions().isEmpty(),
        "Default databaseUser to user should have empty compositions.");
    assertTrue(
        user.getFollowed().isEmpty(), "Default databaseUser to user should have empty followed.");
    assertTrue(
        user.getFollowing().isEmpty(), "Default databaseUser to user should have empty following.");
    assertTrue(
        user.getOrders().isEmpty(), "Default databaseUser to user should have empty orders.");

    UserKeyInfo userKeyInfo = user.getUserKeyInfo();
    assertNotNull(userKeyInfo);
    assertEquals("username", userKeyInfo.getUsername());
    assertEquals("email", userKeyInfo.getEmail());
    assertEquals("password", userKeyInfo.getPassword());
  }
}
