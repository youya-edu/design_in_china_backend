package org.dic.demo.user.database;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {

  long createUser(DatabaseUser user);

  DatabaseUser getUserById(long id);

  DatabaseUser getUserByUsername(String username);

  DatabaseUser getUserByEmail(String email);

  List<DatabaseUser> getAllUsers();

}
