package ru.yandex.practicum.filmorate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.follow.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Qualifier("userDbStorage")
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public User addUser(@NonNull User user) {
        validate(user);
        userStorage.addUser(user);
        return user;
    }

    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()) == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        validate(user);
        userStorage.updateUser(user);
        return user;
    }

    public User getUserById(@NonNull Integer userId) {
        validate(userStorage.getUserById(userId));
        return userStorage.getUserById(userId);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public void addFriend(Integer userId, Integer friendId) {
        validate(userId, friendId);
        if (userStorage.getUserById(userId) != null && userStorage.getUserById(friendId) != null) {
            validate(userStorage.getUserById(userId));
            validate(userStorage.getUserById(friendId));
            friendsStorage.addFriend(userId, friendId);
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    public void removeFriend(Integer userId, Integer friendId) {
        if (userStorage.getUserById(friendId) == null) {
            throw new NotFoundException("Нет пользователя");
        }
        if (userStorage.getUserById(userId) == null) {
            throw new NotFoundException("Нет пользователя");
        }
        if (userStorage.getUserFriends(friendId) == null) {
            throw new NotFoundException("Нет такого друга");
        }
        validate(getUserById(userId));
        validate(getUserById(friendId));
        friendsStorage.deleteFriend(userId, friendId);
    }

    public Collection<User> getMutualFriends(Integer userId, Integer otherId) {
        validate(userId, otherId);
        return userStorage.getMutualFriends(userId, otherId);
    }

    public Collection<User> getUserFriends(Integer userId) {
        User user = userStorage.getUserById(userId);
        if (user == null)
            throw new NotFoundException("Нема такого");
        return userStorage.getUserFriends(userId);
    }

    private void validate(User user) {
        if (user.getEmail() == null) {
            log.warn("Email is empty");
            throw new ConditionsNotMetException("Имейл не должен быть пустым");
        } else if (!user.getEmail().contains("@")) {
            log.warn("Wrong email format (should contain '@')");
            throw new ConditionsNotMetException("Неверный формат имейла (имейл должен содержать символ '@')");
        } else if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.warn("Login is empty");
            throw new ConditionsNotMetException("Логин не может быть пустым");
        } else if (user.getLogin().contains(" ")) {
            log.warn("Wrong login format (should not contain spaces)");
            throw new ConditionsNotMetException("Неверный формат логина (логин не может содержать пробелы)");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("User birthday cannot be in the future");
            throw new ConditionsNotMetException("Дата рождения пользователя не может быть в будущем");
        }
    }

    private void validate(Integer id, Integer otherId) {
        if (id == null) {
            log.warn("Id is null");
            throw new ConditionsNotMetException("Не указан id пользователя.");
        } else if (otherId == null) {
            log.warn("otherId is null");
            throw new ConditionsNotMetException("Не указан id друга.");
        }
    }

    private void validate(Integer id) {
        if (id == null) {
            log.warn("Id is null");
            throw new ConditionsNotMetException("Не указан id пользователя.");
        }
    }

}
