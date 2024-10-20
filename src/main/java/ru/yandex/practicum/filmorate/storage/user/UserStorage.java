package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Qualifier("userDbStorage")
public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    Collection<User> getUsers();

    User getUserById(Integer id);

    Collection<User> getMutualFriends(Integer user1id, Integer user2id);

    Collection<User> getUserFriends(Integer userId);
}
