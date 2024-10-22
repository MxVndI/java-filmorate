package ru.yandex.practicum.filmorate.storage.filmGenre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.DataException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreRowMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmGenreDbStorage implements FilmGenreStorage {

    private final JdbcTemplate jdbc;

    @Override
    public void addFilmGenre(Integer filmId, Integer genreId) {
        final String insert = "INSERT INTO film_genre (film_id, genre_id) values (?, ?)";

        try {
            jdbc.update(insert, filmId, genreId);
        } catch (DuplicateKeyException e) {
            log.warn("Key already exists");
        } catch (DataAccessException e) {
            throw new DataException("Ошибка при добавлении ключей");
        }
    }

    @Override
    public Collection<Genre> getAllFilmGenresByFilmId(Integer filmId) {
        final String getAllByIdQuery = "SELECT g.id AS id, name FROM film_genre AS fg LEFT JOIN genres g ON " +
                "fg.genre_id = g.id WHERE film_id = ?";

        return jdbc.query(getAllByIdQuery, new GenreRowMapper(), filmId);
    }

    @Override
    public void deleteAllFilmGenresByFilmId(Integer filmId) {
        final String deleteQuery = "DELETE FROM film_genre WHERE film_id = ?";
        try {
            jdbc.update(deleteQuery, filmId);
        } catch (DuplicateKeyException e) {
            log.warn("Key duplicate detected. filmId: {}", filmId);
        }
    }

    @Override
    public Map<Integer, Collection<Genre>> getAllFilmGenres(Collection<Film> films) {
        final String getAllQuery = "SELECT fg.film_id, g.id AS genre_id, g.name AS name FROM film_genre fg " +
                "LEFT JOIN genres g ON fg.genre_id = g.id WHERE fg.film_id IN (%s)";

        Map<Integer, Collection<Genre>> filmGenreMap = new HashMap<>();
        Collection<String> ids = films.stream()
                .map(film -> String.valueOf(film.getId()))
                .toList();

        jdbc.query(String.format(getAllQuery, String.join(",", ids)), rs -> {
            Genre genre = new Genre(rs.getInt("genre_id"),
                    rs.getString("name"));

            Integer filmId = rs.getInt("film_id");

            filmGenreMap.putIfAbsent(filmId, new ArrayList<>());
            filmGenreMap.get(filmId).add(genre);
        });

        return filmGenreMap;
    }
}
