package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.follow.FollowStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Qualifier("userDbStorage")
public class UserService {
    private final UserStorage userStorage;
    private final FollowStorage followStorage;

    public User addUser(User user) {
        userStorage.addUser(user);
        return user;
    }

    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()) == null)
            throw new NotFoundException("Нет пользователя с таким id");
        userStorage.updateUser(user);
        return user;
    }

    public User getUserById(Integer userId) {
        return userStorage.getUserById(userId);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public void addFriend(Integer userId, Integer friendId) {
        followStorage.addFriend(userId, friendId);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        followStorage.deleteFriend(userId, friendId);
    }

    public Collection<User> getMutualFriends(Integer userId, Integer otherId) {
        return userStorage.getMutualFriends(userId, otherId);
    }

    public Collection<User> getUserFriends(Integer userId) {
        User user = userStorage.getUserById(userId);
        if (user == null)
            throw new NotFoundException("Нема такого");
        return userStorage.getUserFriends(userId);
    }

}
