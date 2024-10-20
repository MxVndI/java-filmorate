package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public Collection<Mpa> getRatings() {
        final String sql = "select name from ratings";
        return jdbcTemplate.query(sql, new MpaMapper());
    }

    @Override
    public void addRating(String name) {
        final String sql = "insert into ratings (name) values (?, ?)";

        jdbcTemplate.update(sql, name);
    }

    @Override
    public Mpa getRatingById(Integer genreId) {
        final String sql =
                "select name from rating where id = ?";
        return jdbcTemplate.queryForObject(sql, new MpaMapper(), genreId);
    }

    @Override
    public void deleteRatingById(Integer genreId) {
        final String sql = "delete from ratings where genreId = ?";
        jdbcTemplate.update(sql, genreId);
    }
}