package org.dic.demo.util.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

class WebUtilsTest {

  @Test
  void TestUriWithPath_ReqAndPathGiven_UriGenerated() {
    MockHttpServletRequest req = new MockHttpServletRequest();
    req.setProtocol("http");
    req.setServerName("www.test.com");
    req.setRequestURI("/user");
    assertEquals(
        "http://www.test.com/user/test-username",
        WebUtils.uriWithPath(req, "test-username").toString());
  }

  @Test
  void GetAuthorizationHeader_ReqGiven_AuthorizationHeaderGot() {
    MockHttpServletRequest req = new MockHttpServletRequest();
    req.addHeader(HttpHeaders.AUTHORIZATION, "test-authorization-header");
    assertEquals("test-authorization-header", WebUtils.getAuthorizationHeader(req));
  }

  @Test
  void ResolvePathFromUrl_UrlGiven_PathResolved() {
    assertEquals(
        "/img/avatar/default/test-user-avatar.png",
        WebUtils.resolvePathFromUrl("http://www.test.com/img/avatar/default/test-user-avatar.png"));
  }
}
