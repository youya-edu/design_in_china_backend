package org.dic.demo.user.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Profile;

@MybatisTest
@Profile("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoTest {

  @Autowired private UserDao userDao;

  @Test
  void testInsertAndSelect() {
    User user =
        User.builder()
            .userKeyInfo(
                UserKeyInfo.builder()
                    .username("test-username")
                    .email("email@example.com")
                    .password("test-password")
                    .build())
            .build();
    DatabaseUser databaseUser = user.toDatabaseObject();
    userDao.createUser(databaseUser);
    DatabaseUser selectedUser = userDao.getUserById(databaseUser.getId());
    assertEquals("test-username", selectedUser.getUsername());
    assertNotNull(selectedUser.getCreatedAt());
  }
}
