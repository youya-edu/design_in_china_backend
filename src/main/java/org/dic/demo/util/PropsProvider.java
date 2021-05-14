package org.dic.demo.util;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PropsProvider {

  private final Environment environment;

  public String getProperty(String key) {
    return environment.getProperty(key);
  }
}
