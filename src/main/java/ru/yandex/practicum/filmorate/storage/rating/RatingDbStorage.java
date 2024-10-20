package ru.yandex.practicum.filmorate.storage.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;

@Component
@RequiredArgsConstructor
public class RatingDbStorage implements RatingStorage {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public void addRating(String name) {
        final String sql = "insert into ratings (name) values (?, ?)";

        jdbcTemplate.update(sql, name);
    }

    @Override
    public Rating getRatingById(Integer genreId) {
        final String sql =
                "select * from rating where id = ?";
        return jdbcTemplate.queryForObject(sql, new RatingMapper(), genreId);
    }

    @Override
    public void deleteRatingById(Integer genreId) {
        final String sql = "delete from ratings where genreId = ?";
        jdbcTemplate.update(sql, genreId);
    }
}