package ru.yandex.practicum.filmorate.storage.follow;

public interface FollowStorage {
    void addFriend(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);
}