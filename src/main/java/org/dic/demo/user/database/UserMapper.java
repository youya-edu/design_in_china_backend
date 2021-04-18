package org.dic.demo.user.database;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    void insert(DatabaseUser user);
    DatabaseUser select(long id);
}
