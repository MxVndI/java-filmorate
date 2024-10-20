package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

public class UserControllerTest {
    UserStorage userStorage = new InMemoryUserStorage();
    UserService userService = new UserService(userStorage);
    UserController userController = new UserController(userService);

    @Test
    void addUserTest() {
        User user = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        userController.addUser(user);
        Assertions.assertNotNull(userController.getUsers());
    }

    @Test
    void addUserFriendTest() {
        User user = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        User user2 = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        userController.addUser(user);
        userController.addUser(user2);
        userController.addFriend(user.getId(), user2.getId());
        Assertions.assertNotNull(userController.getFriends(user.getId()));
        Assertions.assertNotNull(userController.getFriends(user2.getId()));
    }

    @Test
    void removeUserFriendTest() {
        User user = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        User user2 = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        userController.addUser(user);
        userController.addUser(user2);
        userController.addFriend(user.getId(), user2.getId());
        userController.removeFriend(user.getId(), user2.getId());
        Assertions.assertEquals(userController.getFriends(user.getId()).size(), 0);
        Assertions.assertEquals(userController.getFriends(user2.getId()).size(), 0);
    }


}
