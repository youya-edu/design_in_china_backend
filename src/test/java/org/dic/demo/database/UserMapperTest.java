package org.dic.demo.database;

import org.dic.demo.model.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsertAndSelect() {
        User user = new User();
        user.setUsername("test-username");
        user.setEmail("email@example.com");
        user.setPassword("test-password");
        userMapper.insert(user);
        User selectedUser = userMapper.select(user.getId());
        assertEquals("test-username", selectedUser.getUsername());
        assertNotNull(selectedUser.getCreatedAt());
    }

}
