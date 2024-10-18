package ru.yandex.practicum.filmorate.model;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    public User addUser(User user) {
        if (UserValidator.isValid(user)) {
            user.setId(getNextId());
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Непредвиденная ошибка..");
        }
        return user;
    }

    public User updateUser(User user) {
        if (users.containsKey(user.getId()))
            users.replace(user.getId(), user);
        return user;
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    private Integer getNextId() {
        Integer currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public User getUserById(Integer userId) {
        return users.get(userId);
    }
}
