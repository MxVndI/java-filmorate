package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmGenreDbStorage implements FilmGenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addGenre(String name) {
        final String sql = "insert into genres (name) values (?)";
        try {
            jdbcTemplate.update(sql, name);
        }
        catch (DuplicateKeyException e) {
            log.warn("Не получилось добавить жанр");
        }
    }

    @Override
    public Collection<FilmGenre> getGenreById(Integer genreId) {
        final String sql = "select id, name from genres where id = ?";
        return jdbcTemplate.query(sql, new FilmGenreMapper(), genreId);
    }

    @Override
    public void deleteGenreById(Integer genreId) {
        final String sql = "delete from genres where id = ?";
        jdbcTemplate.update(sql, genreId);
    }

    @Override
    public Collection<FilmGenre> getGenres() {
        final String sql = "select * from genres";
        Collection<FilmGenre> genresMap = new ArrayList<>();
        jdbcTemplate.query(sql, rs -> {
            FilmGenre genre = new FilmGenre(rs.getInt("id"), rs.getString("name"));
            genresMap.add(genre);
        });
        return genresMap;
    }
}