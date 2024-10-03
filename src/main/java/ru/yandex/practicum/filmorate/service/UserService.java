package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User addUser(User user) {
        userStorage.addUser(user);
        return user;
    }

    public User updateUser(/*@Valid*/ User user) {
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
        User user = userStorage.getUserById(userId);
        User friendUser = userStorage.getUserById(friendId);
        user.friends.add(friendUser);
        friendUser.friends.add(user);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        User user = userStorage.getUserById(userId);
        User friendUser = userStorage.getUserById(friendId);
        user.getFriends().remove(friendUser);
        friendUser.getFriends().remove(user);
    }

    public Collection<User> getMutualFriends(Integer userId, Integer otherId) {
        Set<User> mutualFriends = new HashSet<>();
        for (User user: userStorage.getUserById(otherId).getFriends())
            if (userStorage.getUserById(userId).getFriends().contains(user))
                mutualFriends.add(user);
        return mutualFriends;
    }

    public Set<User> getUserFriends(Integer userId) {
        return userStorage.getUserById(userId).getFriends();
    }
}
