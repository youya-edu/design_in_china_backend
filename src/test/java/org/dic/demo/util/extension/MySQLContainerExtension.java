package org.dic.demo.util.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class MySQLContainerExtension implements BeforeAllCallback, AfterAllCallback {

  MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.25"));

  @Override
  public void beforeAll(ExtensionContext context) {
    mysqlContainer.start();
  }

  @Override
  public void afterAll(ExtensionContext context) {
    mysqlContainer.stop();
  }
}
