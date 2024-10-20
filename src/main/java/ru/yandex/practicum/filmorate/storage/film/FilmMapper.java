package ru.yandex.practicum.filmorate.storage.film;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Film()
                .toBuilder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .rating(new Rating(rs.getInt("rating_id"), rs.getString("name")))
                .duration(rs.getInt("duration"))
                .genre(new FilmGenre(rs.getInt("genre_id"), rs.getString("name")))
                .build();
    }
}
