package org.dic.demo.user.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.dic.demo.user.model.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
public class UserMapperTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  void testInsertAndSelect() {
    User user = User.builder()
        .username("test-username")
        .email("email@example.com")
        .password("test-password")
        .build();
    DatabaseUser databaseUser = DatabaseUser.fromDomainObject(user);
    userMapper.insert(databaseUser);
    DatabaseUser selectedUser = userMapper.select(databaseUser.getId());
    assertEquals("test-username", selectedUser.getUsername());
    assertNotNull(selectedUser.getCreatedAt());
  }

}
