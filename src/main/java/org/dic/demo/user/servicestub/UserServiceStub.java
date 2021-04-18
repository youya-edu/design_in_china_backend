package org.dic.demo.user.servicestub;

import org.dic.demo.user.exception.UserNotFoundException;
import org.dic.demo.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserServiceStub {

    private static final AtomicLong idCounter = new AtomicLong(-1);
    private static final Map<Long, User> id2Users = new ConcurrentHashMap<>();
    private static final Map<String, User> username2Users = new ConcurrentHashMap<>();
    private static final Map<String, User> email2Users = new ConcurrentHashMap<>();

    static {
        User luffy = User.builder()
                .id(idCounter.incrementAndGet())
                .username("Luffy")
                .email("luffy@dic.com")
                .password("123")
                .avatar("luffy.jpeg")
                .description("我要成为海贼王！")
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();
        createUser(luffy);

        User zoro = User.builder()
                .id(idCounter.incrementAndGet())
                .username("Zoro")
                .email("zoro@dic.com")
                .password("123")
                .avatar("zoro.jpeg")
                .description("我要成为世界第一大剑豪！")
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();
        createUser(zoro);
    }

    public static User getUserById(long userId) {
        checkIdExistence(userId);
        return id2Users.get(userId);
    }

    public static User getUserByUsername(String username) {
        checkUsernameExistence(username);
        return username2Users.get(username);
    }

    public static User getUserByEmail(String email) {
        checkEmailExistence(email);
        return email2Users.get(email);
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(id2Users.values());
    }

    public static User createUser(User user) {
        id2Users.put(user.getId(), user);
        username2Users.put(user.getUsername(), user);
        email2Users.put(user.getEmail(), user);
        return user;
    }

    public static User updateUser(User user) {
        checkIdExistence(user.getId());
        id2Users.put(user.getId(), user);
        return user;
    }

    public static void deleteUser(long userId) {
        checkIdExistence(userId);
        id2Users.remove(userId);
    }

    private static void checkIdExistence(long userId) {
        if (!id2Users.containsKey(userId)) {
            throw new UserNotFoundException("No such user id: " + userId);
        }
    }

    private static void checkUsernameExistence(String username) {
        if (!username2Users.containsKey(username)) {
            throw new UserNotFoundException("No such username: " + username);
        }
    }

    private static void checkEmailExistence(String email) {
        if (!email2Users.containsKey(email)) {
            throw new UserNotFoundException("No such email: " + email);
        }
    }
}
