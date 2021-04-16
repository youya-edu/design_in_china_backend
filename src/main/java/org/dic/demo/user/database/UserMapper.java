package org.dic.demo.user.database;

import org.apache.ibatis.annotations.Mapper;
import org.dic.demo.user.model.User;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    void insert(User user);
    User select(long id);
}
