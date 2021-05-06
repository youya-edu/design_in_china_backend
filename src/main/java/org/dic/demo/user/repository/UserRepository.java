package org.dic.demo.user.repository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.dic.demo.user.database.DatabaseUser;
import org.dic.demo.user.database.UserDao;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
import org.dic.demo.user.servicestub.UserServiceStub;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepository {

  private final UserDao userDao;

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user selected by id, or null if no such user
   */
  public User getUserById(long userId) {
    DatabaseUser databaseUser = userDao.getUserById(userId);
    if (databaseUser == null) {
      return null;
    }
    return DatabaseUser.toDomainObject(databaseUser);
  }

  /**
   * Gets user by username.
   *
   * @param username the username
   * @return the user selected by username, or null if no such user
   */
  public User getUserByUsername(String username) {
    DatabaseUser databaseUser = userDao.getUserByUsername(username);
    if (databaseUser == null) {
      return null;
    }
    return DatabaseUser.toDomainObject(userDao.getUserByUsername(username));
  }

  /**
   * Gets user by email.
   *
   * @param email the email
   * @return the user selected by email, or null if no such user
   */
  public User getUserByEmail(String email) {
    DatabaseUser databaseUser = userDao.getUserByEmail(email);
    if (databaseUser == null) {
      return null;
    }
    return DatabaseUser.toDomainObject(userDao.getUserByEmail(email));
  }

  /**
   * Gets all users.
   *
   * @return all users in database, or null if there is no user.
   */
  public List<User> getAllUsers() {
    List<DatabaseUser> databaseUser = userDao.getAllUsers();
    if (databaseUser == null) {
      return null;
    }
    return userDao.getAllUsers().stream()
        .map(DatabaseUser::toDomainObject)
        .collect(Collectors.toList());
  }

  /**
   * Create an user.
   *
   * @param userKeyInfo the user key info, including email & username & password
   * @return the user created, with a id auto-generated by database
   */
  public User createUser(UserKeyInfo userKeyInfo) {
    User user = User.builder().userKeyInfo(userKeyInfo).build();
    user.setNickname(user.getUsername());
    long id = userDao.createUser(DatabaseUser.fromDomainObject(user));
    return user.toBuilder().id(id).build();
  }

  public void updateUser(User user) {
    userDao.updateUser(DatabaseUser.fromDomainObject(user));
  }

  public void deleteUser(long userId) {
    UserServiceStub.deleteUser(userId);
  }
}
