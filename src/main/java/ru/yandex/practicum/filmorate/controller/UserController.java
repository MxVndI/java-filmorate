package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        validate(user);
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        validate(user);
        userService.updateUser(user);
        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        validate(id);
        validate(userService.getUserById(id));
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        if (userService.getUserById(id) != null && userService.getUserById(friendId) != null) {
            userService.addFriend(id, friendId);
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        if (userService.getUserById(friendId) == null) {
            throw new NotFoundException("Нет пользователя");
        }
        if (userService.getUserFriends(friendId) == null) {
            throw new NotFoundException("Нет такого друга");
        }

        validate(userService.getUserById(id));
        validate(userService.getUserById(friendId));
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        validate(id, otherId);
        return userService.getMutualFriends(id, otherId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable Integer id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/{friendId}")
    public User getFriendById(@PathVariable Integer id, @PathVariable Integer friendId) {
        validate(id, friendId);
        return userService.getUserById(friendId);
    }

    private void validate(User user) {
        if (user == null) {
            throw new NotFoundException("Не существующий пользователь");
        }
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