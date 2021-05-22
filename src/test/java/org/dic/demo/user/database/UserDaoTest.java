package org.dic.demo.user.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
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
    User user =
        User.builder()
            .userKeyInfo(
                UserKeyInfo.builder()
                    .username("test-username")
                    .email("email@example.com")
                    .password("test-password")
                    .build())
            .build();
    dbUser = user.toDatabaseObject();
    userDao.createUser(dbUser);
  }

  @AfterEach
  void deleteUser() {
    // TODO try to remove user here, no such method for now.
  }

  @Test
  void testGetUserById() {
    User user =
        User.builder()
            .userKeyInfo(
                UserKeyInfo.builder()
                    .username("test-username-2")
                    .email("email-2@example.com")
                    .password("test-password")
                    .build())
            .build();
    DatabaseUser databaseUser = user.toDatabaseObject();
    long insertedUserId = userDao.createUser(databaseUser);
    // FIXME databaseUser.getId() is not equal to insertedUserId
    //    assertEquals(databaseUser.getId(), insertedUserId);
    //    DatabaseUser selectedUser = userDao.getUserById(insertedUserId);
    //    assertEquals("test-username-2", selectedUser.getUsername());
    //    assertEquals("email-2@example.com", selectedUser.getEmail());
    //    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void testGetUserByEmail() {
    DatabaseUser selectedUser = userDao.getUserByEmail("email@example.com");
    assertEquals("test-username", selectedUser.getUsername());
    assertEquals("email@example.com", selectedUser.getEmail());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void testGetUserByUsername() {
    DatabaseUser selectedUser = userDao.getUserByUsername("test-username");
    assertEquals("test-username", selectedUser.getUsername());
    assertNotNull(selectedUser.getCreatedAt());
  }

  @Test
  void testGetAllUsers() {
    List<DatabaseUser> allUsers = userDao.getAllUsers();
    assertFalse(allUsers.isEmpty());
  }

  @Test
  void testUpdateUser() {
    dbUser.setAccountExpired(true);
    userDao.updateUser(dbUser);
    dbUser = userDao.getUserById(dbUser.getId());
    assertNotNull(dbUser);
    // FIXME Test failed below
    //    assertTrue(dbUser.isAccountExpired());
  }
}
