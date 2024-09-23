package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTest {
    UserController userController = new UserController();

    @Test
    void addUserTest() {
        User user = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        userController.addUser(user);
        Assertions.assertNotNull(userController.getUsers());
    }
}
