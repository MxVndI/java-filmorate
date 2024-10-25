package ru.yandex.practicum.filmorate.storage.follow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FriendsDbStorage implements FriendsStorage {
    private final JdbcTemplate jdbc;

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        final String sql = "INSERT INTO follows (user_id, friend_id) VALUES (?, ?)";
        try {
            int rowsAffected = jdbc.update(sql, userId, friendId);
            if (rowsAffected == 1) {
                log.info("Дружеский запрос отправлен успешно");
            } else {
                log.warn("Не удалось отправить дружеский запрос. Возможно, отношение уже существует.");
            }

        } catch (RuntimeException e) {
            log.info("Попытка добавить существующее отношение между пользователями {} и {}", userId, friendId);
        } catch (Exception e) {
            log.error("Ошибка при добавлении дружеского запроса: {}", e.getMessage());
            log.error("Стек вызовов:", e);
        }
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        final String sql = "DELETE FROM follows WHERE user_id = ? AND friend_id = ?";
        try {
            if (checkIfFollowsExists(userId, friendId)) {
                jdbc.update(sql, userId, friendId);
            } else {
                throw new NotFoundException("Нет такой дружбы");
            }
        } catch (Exception e) {
            log.error("User is not friend, or deletion was not successful");
        }
    }

    private boolean checkIfFollowsExists(Integer userId, Integer friendId) {
        String sql = "SELECT COUNT(*) FROM follows WHERE user_id = ? AND friend_id = ?";
        int count = jdbc.queryForObject(sql, Integer.class, userId, friendId);
        return count > 0;
    }
}
