package org.dic.demo.repository.servicestub;

import org.dic.demo.exception.UserNotFoundException;
import org.dic.demo.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserServiceStub {

    private static final AtomicLong idCounter = new AtomicLong(-1);
    private static final Map<Long, User> users = new ConcurrentHashMap<>();

    public User getUserById(long userId) {
        checkUserExistence(userId);
        return users.get(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User createUser(User user) {
        user.setId(idCounter.incrementAndGet());
        if (user.getUsername() == null) {
            user.setUsername(randomUsername());
        }
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        checkUserExistence(user.getId());
        users.put(user.getId(), user);
        return user;
    }

    public void deleteUser(long userId) {
        checkUserExistence(userId);
        users.remove(userId);
    }

    private void checkUserExistence(long userId) {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("No such user: " + userId);
        }
    }

    private String randomUsername() {
        String dictionary = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder dummyName = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            dummyName.append(dictionary.charAt(random.nextInt(dictionary.length())));
        }
        return dummyName.toString();
    }
}
