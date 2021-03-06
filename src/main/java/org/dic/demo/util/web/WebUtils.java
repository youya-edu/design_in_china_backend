package org.dic.demo.util.web;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;

public final class WebUtils {

  private WebUtils() {}

  public static URI addPathToUri(HttpServletRequest req, String path) {
    return UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(req))
        .pathSegment(path)
        .build()
        .toUri();
  }

  public static String getAuthorizationHeader(HttpServletRequest req) {
    return req.getHeader(HttpHeaders.AUTHORIZATION);
  }

  public static String resolvePathFromUri(String uri) {
    if (uri == null) {
      return null;
    }
    return URI.create(uri).getPath();
  }
}
