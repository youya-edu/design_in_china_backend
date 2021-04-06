package org.dic.demo.resource;

import org.dic.demo.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserResource {

    @GetMapping("/{userId}")
    public User getUser(@PathParam("userId") long userId) {
        return new User();
    }

    @GetMapping
    public List<User> getAllUsers() {
        return Arrays.asList(new User());
    }

    @PostMapping
    public User createUser(User user) {
        return new User();
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathParam("userId") long userId) {
        return new User();
    }

    @DeleteMapping("/{userId}")
    public User deleteUser(@PathParam("userId") long userId) {
        return new User();
    }
}
