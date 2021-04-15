package org.dic.demo.database;

import org.apache.ibatis.annotations.Mapper;
import org.dic.demo.model.User;

@Mapper
public interface UserMapper {

    void insert(User user);
    User select(long id);
}
