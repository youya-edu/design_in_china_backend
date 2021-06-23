package org.dic.demo.user.database;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** Bean for providing data for development. Never use this in production...!! */
@Profile("dev")
@Component
@DependsOn("flywayInitializer")
@AllArgsConstructor
public class UserDataProvider {
  private final UserDao userDao;

  @PostConstruct
  private void addCompositionData() {
    if (userDao.userExists()) {
      return;
    }

    // create 500 designers
    IntStream.range(0, 500)
        .forEach(
            (idx) -> {
              DatabaseUser user =
                  DatabaseUser.builder()
                      .username("designer" + idx)
                      .email("d" + idx + "@dic.com")
                      .password("123")
                      .nickname("designer" + idx)
                      .avatar(
                          "http://localhost:8080/img/avatar/default/"
                              + String.format("%03d", idx)
                              + ".png ")
                      .description("我是设计师" + idx)
                      .enabled(true)
                      .build();
              userDao.createUser(user);
              userDao.createUserRoles(
                  Arrays.asList(
                      new DatabaseUserRole(user.getId(), 2),
                      new DatabaseUserRole(user.getId(), 3)));
            });

    // create 500 users
    IntStream.range(0, 500)
        .forEach(
            (idx) -> {
              DatabaseUser user =
                  DatabaseUser.builder()
                      .username("user" + idx)
                      .email("u" + idx + "@dic.com")
                      .password("123")
                      .nickname("user" + idx)
                      .avatar(
                          "http://localhost:8080/img/avatar/default/"
                              + String.format("%03d", idx)
                              + ".png ")
                      .description("我是用户" + idx)
                      .enabled(true)
                      .build();
              userDao.createUser(user);
              userDao.createUserRoles(
                  Collections.singletonList(new DatabaseUserRole(user.getId(), 3)));
            });

    // create 10 admin
    IntStream.range(0, 10)
        .forEach(
            (idx) -> {
              DatabaseUser user =
                  DatabaseUser.builder()
                      .username("admin" + idx)
                      .email("a" + idx + "@dic.com")
                      .password("123")
                      .nickname("admin" + idx)
                      .avatar(
                          "http://localhost:8080/img/avatar/default/"
                              + String.format("%03d", idx)
                              + ".png ")
                      .description("我是管理员" + idx)
                      .enabled(true)
                      .build();
              userDao.createUser(user);
              userDao.createUserRoles(
                  Arrays.asList(
                      new DatabaseUserRole(user.getId(), 1),
                      new DatabaseUserRole(user.getId(), 3)));
            });
  }
}
