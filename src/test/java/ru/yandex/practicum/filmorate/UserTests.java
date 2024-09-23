package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserTests {

    @Test
    void createUserEmptyNameTest() {
        User user = new User("ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getName());
    }

    @Test
    void createUserTest() {
        User user = new User("ahahaha", "ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        Assertions.assertNotNull(user);
    }

    @Test
    void equalsUsersTest() {
        User user1 = new User("ahahaha", "ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        User user2 = new User("ahahaha", "ozzinad", "fdsfds@fsd.cd", LocalDate.of(2004, 12, 4));
        Assertions.assertEquals(user1, user2);
    }
}
