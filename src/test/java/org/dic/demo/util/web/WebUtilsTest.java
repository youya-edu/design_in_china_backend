package org.dic.demo.util.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

class WebUtilsTest {

  @Nested
  class AddPathToUriTest {

    @Test
    @DisplayName("If path is valid, new URI will be generated.")
    void ifPathIsValid() {
      MockHttpServletRequest req = new MockHttpServletRequest();
      req.setProtocol("http");
      req.setServerName("www.test.com");
      req.setRequestURI("/user");

      String expectedUri = "http://www.test.com/user/test-username";
      String actualUri = WebUtils.addPathToUri(req, "test-username").toString();

      assertEquals(expectedUri, actualUri);
    }

    @Test
    @DisplayName("If path is null, original URI will be returned.")
    void isPathIsNull() {
      MockHttpServletRequest req = new MockHttpServletRequest();
      req.setProtocol("http");
      req.setServerName("www.test.com");
      req.setRequestURI("/user");

      String expectedUri = "http://www.test.com/user";
      String actualUri = WebUtils.addPathToUri(req, null).toString();

      assertEquals(expectedUri, actualUri);
    }
  }

  @Nested
  class GetAuthorizationHeader {
    @Test
    @DisplayName("If authorization header exists, it's value will be returned.")
    void ifHeaderExists() {
      MockHttpServletRequest req = new MockHttpServletRequest();
      req.addHeader(HttpHeaders.AUTHORIZATION, "test-authorization-header");

      String expectedAuthorizationHeader = "test-authorization-header";
      String actualAuthorizationHeader = WebUtils.getAuthorizationHeader(req);

      assertEquals(expectedAuthorizationHeader, actualAuthorizationHeader);
    }

    @Test
    @DisplayName("If authorization header does not exist, null will be returned.")
    void ifHeaderNotExist() {
      MockHttpServletRequest req = new MockHttpServletRequest();

      String actualAuthorizationHeader = WebUtils.getAuthorizationHeader(req);

      assertNull(actualAuthorizationHeader);
    }
  }

  @Nested
  class ResolvePathFromUri {

    @Test
    @DisplayName("If uri is valid, resolved path will be returned.")
    void ifUriIsValid() {
      String expectedPath = "/img/avatar/default/test-user-avatar.png";
      String actualPath =
          WebUtils.resolvePathFromUri(
              "http://www.test.com/img/avatar/default/test-user-avatar.png");

      assertEquals(expectedPath, actualPath);
    }

    @Test
    @DisplayName("If uri is null, null will be returned.")
    void ifUriIsNull() {
      String actualPath = WebUtils.resolvePathFromUri(null);
      assertNull(actualPath);
    }
  }
}
