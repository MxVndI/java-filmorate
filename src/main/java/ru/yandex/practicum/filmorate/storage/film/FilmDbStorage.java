package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.*;

import java.sql.PreparedStatement;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private static final String FILMS_SQL = "select * from films";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film addFilm(Film film) {
        final String sql = "insert into films (name, release_date, description, duration, rating_id, genre_id) " +
                "values (?, ?, ?, ?, ?, ?)";
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, film.getName());
            preparedStatement.setObject(2, film.getReleaseDate());
            preparedStatement.setString(3, film.getDescription());
            preparedStatement.setInt(4, film.getDuration());
            preparedStatement.setInt(5, film.getRating().getId());
            preparedStatement.setInt(6, film.getGenre().getId());
            return preparedStatement;
        }, generatedKeyHolder);
        int filmId = Objects.requireNonNull(generatedKeyHolder.getKey()).intValue();
        film.setId(filmId);
        return film;
    }

    @Override
    public Film getFilmById(Integer filmId) {
        List<Film> films = jdbcTemplate.query(FILMS_SQL.concat(" where id = ?"), new FilmMapper(), filmId);
        return films.get(0);
    }

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> films = jdbcTemplate.query(FILMS_SQL, new FilmMapper());
        return films;
    }

    @Override
    public Film updateFilm(Film film) {
        final String sql = "update films set name = ?, release_date = ?, description = ?, duration = ?, " +
                "rating_id = ? genre_id = ? where id = ?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getReleaseDate(),
                film.getDescription(),
                film.getDuration(),
                film.getRating().getId(),
                film.getGenre().getId(),
                film.getId()
        );
        return film;
    }

    public Collection<Film> getPopularFilms(Integer count) {
        String sql = "select count(*) from likes where film_id = ? order by count(*) desc limit ?";
        Collection<Film> films = jdbcTemplate.query(String.format(sql), new FilmMapper(), count);
        return films;
    }

    public boolean deleteFilmById(Integer id) {
        final String sql = "delete from films where id = ?";
        int status = jdbcTemplate.update(sql, id);
        return status != 0;
    }

}
