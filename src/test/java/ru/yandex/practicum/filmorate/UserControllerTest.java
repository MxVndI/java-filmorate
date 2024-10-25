package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.follow.FriendsDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

@SpringBootTest
public class UserControllerTest {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    FriendsDbStorage friendsDbStorage = new FriendsDbStorage(jdbcTemplate);
    UserStorage userStorage = new UserDbStorage(friendsDbStorage, jdbcTemplate);
    UserService userService = new UserService(userStorage, friendsDbStorage);
    UserController userController = new UserController(userService);

    @Test
    public void ifEmailIsBlankThrowException() {
        User user = new User();
        user.setEmail("");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user));
    }

    @Test
    public void ifLoginIsBlankThrowException() {
        User user = new User();
        user.setEmail("abcd@gmail.com");
        user.setLogin("");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user));
        Assertions.assertEquals("Логин не может быть пустым", exception.getMessage());
    }

    @Test
    public void ifBirthdayIsAfterInstantNowThrowException() {
        User user = new User();
        user.setEmail("abcd@gmail.com");
        user.setLogin("abcd");
        user.setBirthday(LocalDate.of(20000, 10, 10));
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user));
    }

}