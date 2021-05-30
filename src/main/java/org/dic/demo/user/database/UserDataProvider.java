package org.dic.demo.user.database;

import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

/** Bean for providing data for development. Never use this in production...!! */
@Profile("dev")
// @Component
@DependsOn("flywayInitializer")
@AllArgsConstructor
public class UserDataProvider {
  private final UserDao userDao;

  @PostConstruct
  private void addCompositionData() {
    if (userDao.userExists()) {
      return;
    }

    DatabaseUser user =
        DatabaseUser.builder()
            .username("designer1")
            .email("d1@dic.com")
            .password("123")
            .nickname("designer1")
            .avatar("http://localhost:8080/img/avatar/default/001.png")
            .build();
    userDao.createUser(user);
  }
}
