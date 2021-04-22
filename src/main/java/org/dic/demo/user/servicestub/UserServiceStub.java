package org.dic.demo.user.servicestub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.dic.demo.user.exception.UserNotFoundException;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;

public class UserServiceStub {

  private static final AtomicLong idCounter = new AtomicLong(-1);
  private static final Map<Long, User> id2Users = new ConcurrentHashMap<>();
  private static final Map<String, User> username2Users = new ConcurrentHashMap<>();
  private static final Map<String, User> email2Users = new ConcurrentHashMap<>();

  static {
    User luffy = User.builder()
        .id(idCounter.incrementAndGet())
        .userKeyInfo(
            UserKeyInfo.builder()
                .username("Luffy")
                .email("luffy@dic.com")
                .password("123")
                .build()
        )
        .avatar("luffy.jpeg")
        .description("我要成为海贼王！")
        .enabled(true)
        .accountNonLocked(true)
        .accountNonExpired(true)
        .credentialsNonExpired(true)
        .build();
    createUser(luffy);

    User zoro = User.builder()
        .id(idCounter.incrementAndGet())
        .userKeyInfo(
            UserKeyInfo.builder()
                .username("Zoro")
                .email("zoro@dic.com")
                .password("123")
                .build()
        )
        .avatar("zoro.jpeg")
        .description("我要成为世界第一大剑豪！")
        .enabled(true)
        .accountNonLocked(true)
        .accountNonExpired(true)
        .credentialsNonExpired(true)
        .build();
    createUser(zoro);
  }

  public static User getUserById(long userId) {
    return id2Users.get(userId);
  }

  public static User getUserByUsername(String username) {
    return username2Users.get(username);
  }

  public static User getUserByEmail(String email) {
    return email2Users.get(email);
  }

  public static List<User> getAllUsers() {
    return new ArrayList<>(id2Users.values());
  }

  public static User createUser(User user) {
    id2Users.put(user.getId(), user);
    username2Users.put(user.getUsername(), user);
    email2Users.put(user.getEmail(), user);
    return user;
  }

  public static User updateUser(User user) {
    if (!checkIfIdExisted(user.getId())) {
      throw new UserNotFoundException();
    }
    id2Users.put(user.getId(), user);
    return user;
  }

  public static void deleteUser(long userId) {
    if (!checkIfIdExisted(userId)) {
      throw new UserNotFoundException();
    }
    id2Users.remove(userId);
  }

  private static boolean checkIfIdExisted(long userId) {
    return id2Users.containsKey(userId);
  }

  private static boolean checkIfUsernameExisted(String username) {
    return username2Users.containsKey(username);
  }

  private static boolean checkIfEmailExisted(String email) {
    return email2Users.containsKey(email);
  }
}
