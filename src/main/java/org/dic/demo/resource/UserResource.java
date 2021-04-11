package org.dic.demo.resource;

import org.dic.demo.model.User;
import org.dic.demo.model.UserCollection;
import org.dic.demo.service.UserService;
import org.dic.demo.util.http.HttpUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<UserCollection> getAllUsers() {
        List<User> users = userService.getAllUsers();
        UserCollection userCollection = new UserCollection();
        userCollection.setUsers(users);
        userCollection.setSize(users.size());
        return ResponseEntity.ok(userCollection);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User payload, HttpServletRequest req) {
        User newUser = userService.createUser(payload);
        return ResponseEntity
                .created(HttpUtils.uriWithPath(req, String.valueOf(newUser.getId())))
                .body(newUser);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User payload) {
        return ResponseEntity.ok(userService.updateUser(payload));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}