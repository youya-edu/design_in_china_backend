package org.dic.demo.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dic.demo.util.HttpUtils;
import org.dic.demo.util.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final UserDetailsService userService;

  public JwtAuthorizationFilter(UserDetailsService userService) {
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorizationHeader = HttpUtils.getAuthorizationHeader(request);
    String jwtToken = null;
    String username = null;
    if (AuthenticationType.isBearer(authorizationHeader)) {
      jwtToken = JwtUtils.getToken(authorizationHeader);
      username = JwtUtils.extractUsername(jwtToken);
    }

    if (username != null) {
      UserDetails userDetails = userService.loadUserByUsername(username);
      if (JwtUtils.validateToken(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, jwtToken, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
