package org.dic.demo.security;

import org.dic.demo.user.exception.LoginInfoNotEnoughException;
import org.dic.demo.user.exception.UserNotFoundException;
import org.dic.demo.user.model.User;
import org.dic.demo.user.service.UserService;
import org.dic.demo.util.JwtUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
)
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginInfo loginInfo) {
        User user = userService.authenticateUser(loginInfo);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), loginInfo.getPassword())
        );
        String jwtToken = JwtUtils.generateToken(user);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, user));
    }
}
