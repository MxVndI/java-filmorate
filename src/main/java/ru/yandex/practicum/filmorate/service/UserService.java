package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Qualifier("userDbStorage")
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    public User addUser(User user) {
        if (!UserValidator.isValid(user))
            throw new ValidationException("Некорректные данные");
        userStorage.addUser(user);
        return user;
    }

    public User updateUser(User user) {
        if (!UserValidator.isValid(user))
            throw new ValidationException("Некорректные данные");
        checkIsCreatedObject(user.getId());
        if (userStorage.getUserById(user.getId()) == null)
            throw new NotFoundException("Нет пользователя с таким id");
        userStorage.updateUser(user);
        return user;
    }

    public User getUserById(Integer userId) {
        checkIsCreatedObject(userId);
        return userStorage.getUserById(userId);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public void addFriend(Integer userId, Integer friendId) {
        checkIsCreatedObject(userId, friendId);
        friendshipStorage.addFriend(userId, userId);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        checkIsCreatedObject(userId, friendId);
        friendshipStorage.deleteFriend(userId, friendId);
    }

    public Collection<User> getMutualFriends(Integer userId, Integer otherId) {
        checkIsCreatedObject(userId, otherId);
        return userStorage.getMutualFriends(userId, otherId);
    }

    public Collection<User> getUserFriends(Integer userId) {
        User user = userStorage.getUserById(userId);
        checkUserIsNotFound(user, userId);
        if (user == null)
            throw new NotFoundException("Нема такого");
        return userStorage.getUserFriends(userId);
    }

    private void checkUserIsNotFound(User user, Integer id) {
        if (UserValidator.isUserNull(user)) {
            throw new NotFoundException(String.format("Нема такого", id));
        }
    }

    private void checkIsCreatedObject(Integer userId, Integer otherId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(otherId);
        checkUserIsNotFound(friend, otherId);
        checkUserIsNotFound(user, userId);
        if (user == null || friend == null)
            throw new NotFoundException("Нема");
    }

    private void checkIsCreatedObject(Integer userId) {
        User user = userStorage.getUserById(userId);
        checkUserIsNotFound(user, userId);
        if (user == null)
            throw new NotFoundException("Нема");
    }
}
