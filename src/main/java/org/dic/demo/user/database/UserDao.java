package org.dic.demo.user.database;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {

  boolean userExists();

  void createUser(DatabaseUser user);

  int createUserRoles(List<DatabaseUserRole> roles);

  DatabaseUser getUserById(long id);

  DatabaseUser getUserByUsername(String username);

  DatabaseUser getUserByEmail(String email);

  List<DatabaseUser> getDesigners();

  List<DatabaseUser> getAllUsers();

  void updateUser(DatabaseUser user);
}
