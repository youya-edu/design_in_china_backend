package org.dic.demo.util.web;

import lombok.AllArgsConstructor;
import org.dic.demo.util.PropsProvider;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebHelper {

  public static final String HTTP_PROTOCOL = "http://";
  public static final String LOCALHOST = "localhost";

  private final PropsProvider propsProvider;

  public String getOrigin() {
    return HTTP_PROTOCOL + LOCALHOST + ":" + propsProvider.getProperty("server.port");
  }
}
