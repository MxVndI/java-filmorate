package ru.yandex.practicum.filmorate.storage.rating;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMapper implements RowMapper<Rating> {

    @Override
    public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Rating()
                .toBuilder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}