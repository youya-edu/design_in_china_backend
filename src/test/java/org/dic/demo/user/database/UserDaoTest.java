package org.dic.demo.user.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.dic.demo.util.extension.MySQLContainerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Profile;

@MybatisTest
@Profile("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith({MySQLContainerExtension.class})
public class UserDaoTest {

  @Autowired private UserDao userDao;

  private DatabaseUser createUser(
      boolean accountExpired,
      boolean accountLocked,
      String avatar,
      boolean credentialsExpired,
      String email,
      String description,
      boolean enabled,
      String username,
      String nickname,
      String password,
      String phone) {
    DatabaseUser databaseUser =
        DatabaseUser.builder()
            .accountExpired(accountExpired)
            .accountLocked(accountLocked)
            .avatar(avatar)
            .credentialsExpired(credentialsExpired)
            .email(email)
            .description(description)
            .enabled(enabled)
            .username(username)
            .nickname(nickname)
            .password(password)
            .phone(phone)
            .build();
    userDao.createUser(databaseUser);
    return databaseUser;
  }

  @Test
  void testGetUserById() {
    DatabaseUser databaseUser =
        createUser(
            false,
            false,
            "avatar-testGetUserById",
            false,
            "email-testGetUserById",
            "description-testGetUserById",
            false,
            "username-testGetUserById",
            "nickname-testGetUserById",
            "password-testGetUserById",
            "phone-testGetUserById");

    DatabaseUser selectedUser = userDao.getUserById(databaseUser.getId());
    assertNotNull(selectedUser);
    assertFalse(selectedUser.isAccountExpired());
    assertFalse(selectedUser.isAccountLocked());
    assertEquals("avatar-testGetUserById", selectedUser.getAvatar());
    assertFalse(selectedUser.isCredentialsExpired());
    assertEquals("email-testGetUserById", selectedUser.getEmail());
    assertEquals("description-testGetUserById", selectedUser.getDescription());
    assertFalse(selectedUser.isEnabled());
    assertEquals("username-testGetUserById", selectedUser.getUsername());
    assertEquals("nickname-testGetUserById", selectedUser.getNickname());
    assertEquals("phone-testGetUserById", selectedUser.getPhone());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void testGetUserByEmail() {
    createUser(
        false,
        false,
        "avatar-testGetUserByEmail",
        false,
        "email-testGetUserByEmail",
        "description-testGetUserByEmail",
        false,
        "username-testGetUserByEmail",
        "nickname-testGetUserByEmail",
        "password-testGetUserByEmail",
        "phone-testGetUserByEmail");
    DatabaseUser selectedUser = userDao.getUserByEmail("email-testGetUserByEmail");
    assertNotNull(selectedUser);
    assertFalse(selectedUser.isAccountExpired());
    assertFalse(selectedUser.isAccountLocked());
    assertEquals("avatar-testGetUserByEmail", selectedUser.getAvatar());
    assertFalse(selectedUser.isCredentialsExpired());
    assertEquals("email-testGetUserByEmail", selectedUser.getEmail());
    assertEquals("description-testGetUserByEmail", selectedUser.getDescription());
    assertFalse(selectedUser.isEnabled());
    assertEquals("username-testGetUserByEmail", selectedUser.getUsername());
    assertEquals("nickname-testGetUserByEmail", selectedUser.getNickname());
    assertEquals("phone-testGetUserByEmail", selectedUser.getPhone());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void testGetUserByUsername() {
    createUser(
        false,
        false,
        "avatar-testGetUserByUsername",
        false,
        "email-testGetUserByUsername",
        "description-testGetUserByUsername",
        false,
        "username-testGetUserByUsername",
        "nickname-testGetUserByUsername",
        "password-testGetUserByUsername",
        "phone-testGetUserByUsername");
    DatabaseUser selectedUser = userDao.getUserByEmail("email-testGetUserByUsername");
    assertNotNull(selectedUser);
    assertFalse(selectedUser.isAccountExpired());
    assertFalse(selectedUser.isAccountLocked());
    assertEquals("avatar-testGetUserByUsername", selectedUser.getAvatar());
    assertFalse(selectedUser.isCredentialsExpired());
    assertEquals("email-testGetUserByUsername", selectedUser.getEmail());
    assertEquals("description-testGetUserByUsername", selectedUser.getDescription());
    assertFalse(selectedUser.isEnabled());
    assertEquals("username-testGetUserByUsername", selectedUser.getUsername());
    assertEquals("nickname-testGetUserByUsername", selectedUser.getNickname());
    assertEquals("phone-testGetUserByUsername", selectedUser.getPhone());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void testGetAllUsers() {
    createUser(
        false,
        false,
        "avatar-1",
        false,
        "email-1",
        "description-1",
        false,
        "username-1",
        "nickname-1",
        "password-1",
        "phone-1");
    createUser(
        false,
        false,
        "avatar-2",
        false,
        "email-2",
        "description-2",
        false,
        "username-2",
        "nickname-2",
        "password-2",
        "phone-2");
    List<DatabaseUser> allUsers = userDao.getAllUsers();
    assertFalse(allUsers.isEmpty());
    assertEquals(2, allUsers.size());
  }

  @Test
  void testUpdateUser() {
    DatabaseUser databaseUser =
        createUser(
            false,
            false,
            "avatar-testUpdateUser",
            false,
            "email-testUpdateUser",
            "description-testUpdateUser",
            false,
            "username-testUpdateUser",
            "nickname-testUpdateUser",
            "password-testUpdateUser",
            "phone-testUpdateUser");
    databaseUser.setAccountExpired(true);
    databaseUser.setAccountLocked(true);
    databaseUser.setAvatar("avatar-testUpdateUser-modified");
    databaseUser.setCredentialsExpired(true);
    databaseUser.setEmail("email-testUpdateUser-modified");
    databaseUser.setDescription("description-testUpdateUser-modified");
    databaseUser.setEnabled(true);
    databaseUser.setUsername("username-testUpdateUser-modified");
    databaseUser.setNickname("nickname-testUpdateUser-modified");
    databaseUser.setPhone("phone-testUpdateUser-modified");
    databaseUser.setPassword("password-testUpdateUser-modified");

    userDao.updateUser(databaseUser);
    DatabaseUser selectedUser = userDao.getUserById(databaseUser.getId());

    assertTrue(selectedUser.isAccountExpired());
    assertTrue(selectedUser.isAccountLocked());
    assertEquals("avatar-testUpdateUser-modified", selectedUser.getAvatar());
    assertTrue(selectedUser.isCredentialsExpired());
    assertEquals("email-testUpdateUser-modified", selectedUser.getEmail());
    assertEquals("description-testUpdateUser-modified", selectedUser.getDescription());
    assertTrue(selectedUser.isEnabled());
    assertEquals("username-testUpdateUser-modified", selectedUser.getUsername());
    assertEquals("nickname-testUpdateUser-modified", selectedUser.getNickname());
    assertEquals("phone-testUpdateUser-modified", selectedUser.getPhone());
    assertNotNull(selectedUser);
  }
}
