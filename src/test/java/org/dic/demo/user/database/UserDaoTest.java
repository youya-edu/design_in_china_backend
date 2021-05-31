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

  @Test
  void getUserById() {
    DatabaseUser databaseUser =
        DatabaseUser.builder()
            .accountExpired(false)
            .accountLocked(false)
            .avatar("avatar-getUserById")
            .credentialsExpired(false)
            .email("email-getUserById")
            .description("description-getUserById")
            .enabled(false)
            .username("username-getUserById")
            .nickname("nickname-getUserById")
            .password("password-getUserById")
            .phone("phone-getUserById")
            .build();
    userDao.createUser(databaseUser);

    DatabaseUser selectedUser = userDao.getUserById(databaseUser.getId());
    assertNotNull(selectedUser);
    assertFalse(selectedUser.isAccountExpired());
    assertFalse(selectedUser.isAccountLocked());
    assertEquals("avatar-getUserById", selectedUser.getAvatar());
    assertFalse(selectedUser.isCredentialsExpired());
    assertEquals("email-getUserById", selectedUser.getEmail());
    assertEquals("description-getUserById", selectedUser.getDescription());
    assertFalse(selectedUser.isEnabled());
    assertEquals("username-getUserById", selectedUser.getUsername());
    assertEquals("nickname-getUserById", selectedUser.getNickname());
    assertEquals("phone-getUserById", selectedUser.getPhone());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void getUserByEmail() {
    DatabaseUser databaseUser =
        DatabaseUser.builder()
            .accountExpired(false)
            .accountLocked(false)
            .avatar("avatar-getUserByEmail")
            .credentialsExpired(false)
            .email("email-getUserByEmail")
            .description("description-getUserByEmail")
            .enabled(false)
            .username("username-getUserByEmail")
            .nickname("nickname-getUserByEmail")
            .password("password-getUserByEmail")
            .phone("phone-getUserByEmail")
            .build();
    userDao.createUser(databaseUser);

    DatabaseUser selectedUser = userDao.getUserByEmail("email-getUserByEmail");
    assertNotNull(selectedUser);
    assertFalse(selectedUser.isAccountExpired());
    assertFalse(selectedUser.isAccountLocked());
    assertEquals("avatar-getUserByEmail", selectedUser.getAvatar());
    assertFalse(selectedUser.isCredentialsExpired());
    assertEquals("email-getUserByEmail", selectedUser.getEmail());
    assertEquals("description-getUserByEmail", selectedUser.getDescription());
    assertFalse(selectedUser.isEnabled());
    assertEquals("username-getUserByEmail", selectedUser.getUsername());
    assertEquals("nickname-getUserByEmail", selectedUser.getNickname());
    assertEquals("phone-getUserByEmail", selectedUser.getPhone());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void getUserByUsername() {
    DatabaseUser databaseUser =
        DatabaseUser.builder()
            .accountExpired(false)
            .accountLocked(false)
            .avatar("avatar-getUserByUsername")
            .credentialsExpired(false)
            .email("email-getUserByUsername")
            .description("description-getUserByUsername")
            .enabled(false)
            .username("username-getUserByUsername")
            .nickname("nickname-getUserByUsername")
            .password("password-getUserByUsername")
            .phone("phone-getUserByUsername")
            .build();
    userDao.createUser(databaseUser);

    DatabaseUser selectedUser = userDao.getUserByUsername("username-getUserByUsername");
    assertNotNull(selectedUser);
    assertFalse(selectedUser.isAccountExpired());
    assertFalse(selectedUser.isAccountLocked());
    assertEquals("avatar-getUserByUsername", selectedUser.getAvatar());
    assertFalse(selectedUser.isCredentialsExpired());
    assertEquals("email-getUserByUsername", selectedUser.getEmail());
    assertEquals("description-getUserByUsername", selectedUser.getDescription());
    assertFalse(selectedUser.isEnabled());
    assertEquals("username-getUserByUsername", selectedUser.getUsername());
    assertEquals("nickname-getUserByUsername", selectedUser.getNickname());
    assertEquals("phone-getUserByUsername", selectedUser.getPhone());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void getAllUsers() {
    DatabaseUser databaseUser =
        DatabaseUser.builder()
            .accountExpired(false)
            .accountLocked(false)
            .avatar("avatar-1")
            .credentialsExpired(false)
            .email("email-1")
            .description("description-1")
            .enabled(false)
            .username("username-1")
            .nickname("nickname-1")
            .password("password-1")
            .phone("phone-1")
            .build();
    userDao.createUser(databaseUser);
    databaseUser =
        DatabaseUser.builder()
            .accountExpired(false)
            .accountLocked(false)
            .avatar("avatar-2")
            .credentialsExpired(false)
            .email("email-2")
            .description("description-2")
            .enabled(false)
            .username("username-2")
            .nickname("nickname-2")
            .password("password-2")
            .phone("phone-2")
            .build();
    userDao.createUser(databaseUser);

    List<DatabaseUser> allUsers = userDao.getAllUsers();
    assertFalse(allUsers.isEmpty());
    assertEquals(2, allUsers.size());
  }

  @Test
  void updateUser() {
    DatabaseUser databaseUser =
        DatabaseUser.builder()
            .accountExpired(false)
            .accountLocked(false)
            .avatar("avatar-updateUser")
            .credentialsExpired(false)
            .email("email-updateUser")
            .description("description-updateUser")
            .enabled(false)
            .username("username-updateUser")
            .nickname("nickname-updateUser")
            .password("password-updateUser")
            .phone("phone-updateUser")
            .build();
    userDao.createUser(databaseUser);
    databaseUser.setAccountExpired(true);
    databaseUser.setAccountLocked(true);
    databaseUser.setAvatar("avatar-updateUser-modified");
    databaseUser.setCredentialsExpired(true);
    databaseUser.setEmail("email-updateUser-modified");
    databaseUser.setDescription("description-updateUser-modified");
    databaseUser.setEnabled(true);
    databaseUser.setUsername("username-updateUser-modified");
    databaseUser.setNickname("nickname-updateUser-modified");
    databaseUser.setPhone("phone-updateUser-modified");
    databaseUser.setPassword("password-updateUser-modified");

    userDao.updateUser(databaseUser);
    DatabaseUser selectedUser = userDao.getUserById(databaseUser.getId());

    assertNotNull(selectedUser);
    assertTrue(selectedUser.isAccountExpired());
    assertTrue(selectedUser.isAccountLocked());
    assertEquals("avatar-updateUser-modified", selectedUser.getAvatar());
    assertTrue(selectedUser.isCredentialsExpired());
    assertEquals("email-updateUser-modified", selectedUser.getEmail());
    assertEquals("description-updateUser-modified", selectedUser.getDescription());
    assertTrue(selectedUser.isEnabled());
    assertEquals("username-updateUser-modified", selectedUser.getUsername());
    assertEquals("nickname-updateUser-modified", selectedUser.getNickname());
    assertEquals("phone-updateUser-modified", selectedUser.getPhone());
    assertNotNull(selectedUser.getCreatedAt());
  }
}
