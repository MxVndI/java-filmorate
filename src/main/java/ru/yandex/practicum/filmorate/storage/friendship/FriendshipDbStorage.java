package ru.yandex.practicum.filmorate.storage.friendship;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendshipDbStorage implements FriendshipStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        final String sql = "insert into friendship (user_id, friend_id) values (" + userId + ", " + friendId + ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        final String sql = "delete from friendship where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }
}