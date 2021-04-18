package org.dic.demo.util;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;

public final class HttpUtils {

  public static URI uriWithPath(HttpServletRequest req, String path) {
    return UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(req)).pathSegment(path)
        .build().toUri();
  }

  public static String getAuthorizationHeader(HttpServletRequest req) {
    return req.getHeader(HttpHeaders.AUTHORIZATION);
  }
}
