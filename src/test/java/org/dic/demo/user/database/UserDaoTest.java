package org.dic.demo.user.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.dic.demo.util.extension.MySQLContainerExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

  private DatabaseUser dbUser; // Used for testing

  @BeforeEach
  void insertUser() {
    dbUser =
        DatabaseUser.builder()
            .accountExpired(false)
            .accountLocked(false)
            .avatar("avatar-1")
            .credentialsExpired(false)
            .email("email-1@dic.com")
            .description("description-1")
            .enabled(false)
            .username("username-1")
            .nickname("nickname-1")
            .password("password-1")
            .phone("000-0000-0001")
            .build();
    userDao.createUser(dbUser);
  }

  @AfterEach
  void deleteUser() {
    // TODO try to remove user here, no such method for now.
  }

  @Test
  void testGetUserById() {
    DatabaseUser databaseUser =
        DatabaseUser.builder()
            .accountExpired(false)
            .accountLocked(false)
            .avatar("avatar-2")
            .credentialsExpired(false)
            .email("email-2@dic.com")
            .description("description-2")
            .enabled(false)
            .username("username-2")
            .nickname("nickname-2")
            .password("password-2")
            .phone("000-0000-0002")
            .build();
    userDao.createUser(databaseUser);
    assertFalse(databaseUser.isAccountExpired());
    assertFalse(databaseUser.isAccountLocked());
    assertEquals("avatar-2", databaseUser.getAvatar());
    assertFalse(databaseUser.isCredentialsExpired());
    assertEquals("email-2@dic.com", databaseUser.getEmail());
    assertEquals("description-2", databaseUser.getDescription());
    assertFalse(databaseUser.isEnabled());
    assertEquals("username-2", databaseUser.getUsername());
    assertEquals("nickname-2", databaseUser.getNickname());
    assertEquals("000-0000-0002", databaseUser.getPhone());
    assertNull(databaseUser.getCreatedAt(), "默认新增的databaseUser是没有createAt值的");

    DatabaseUser selectedUser = userDao.getUserById(databaseUser.getId());
    assertNotNull(selectedUser);
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void testGetUserByEmail() {
    DatabaseUser selectedUser = userDao.getUserByEmail("email-1@dic.com");
    assertFalse(selectedUser.isAccountExpired());
    assertFalse(selectedUser.isAccountLocked());
    assertEquals("avatar-1", selectedUser.getAvatar());
    assertFalse(selectedUser.isCredentialsExpired());
    assertEquals("email-1@dic.com", selectedUser.getEmail());
    assertEquals("description-1", selectedUser.getDescription());
    assertFalse(selectedUser.isEnabled());
    assertEquals("username-1", selectedUser.getUsername());
    assertEquals("nickname-1", selectedUser.getNickname());
    assertEquals("000-0000-0001", selectedUser.getPhone());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void testGetUserByUsername() {
    DatabaseUser selectedUser = userDao.getUserByUsername("username-1");
    assertFalse(selectedUser.isAccountExpired());
    assertFalse(selectedUser.isAccountLocked());
    assertEquals("avatar-1", selectedUser.getAvatar());
    assertFalse(selectedUser.isCredentialsExpired());
    assertEquals("email-1@dic.com", selectedUser.getEmail());
    assertEquals("description-1", selectedUser.getDescription());
    assertFalse(selectedUser.isEnabled());
    assertEquals("username-1", selectedUser.getUsername());
    assertEquals("nickname-1", selectedUser.getNickname());
    assertEquals("000-0000-0001", selectedUser.getPhone());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void testGetAllUsers() {
    List<DatabaseUser> allUsers = userDao.getAllUsers();
    assertFalse(allUsers.isEmpty());
  }

  @Test
  void testUpdateUser() {
    DatabaseUser databaseUser = dbUser.toBuilder().build(); // make a clone
    databaseUser.setAccountExpired(true);
    databaseUser.setAccountLocked(true);
    databaseUser.setAvatar("avatar-3");
    databaseUser.setCredentialsExpired(true);
    databaseUser.setEmail("email-3@dic.com");
    databaseUser.setDescription("description-3");
    databaseUser.setEnabled(true);
    databaseUser.setUsername("username-3");
    databaseUser.setNickname("nickname-3");
    databaseUser.setPhone("000-0000-0003");
    databaseUser.setPassword("password-3");

    userDao.updateUser(databaseUser);

    assertTrue(databaseUser.isAccountExpired());
    assertTrue(databaseUser.isAccountLocked());
    assertEquals("avatar-3", databaseUser.getAvatar());
    assertTrue(databaseUser.isCredentialsExpired());
    assertEquals("email-3@dic.com", databaseUser.getEmail());
    assertEquals("description-3", databaseUser.getDescription());
    assertTrue(databaseUser.isEnabled());
    assertEquals("username-3", databaseUser.getUsername());
    assertEquals("nickname-3", databaseUser.getNickname());
    assertEquals("000-0000-0003", databaseUser.getPhone());
    assertNotNull(databaseUser);
  }
}
