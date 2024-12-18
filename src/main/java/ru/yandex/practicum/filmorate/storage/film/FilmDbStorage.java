package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.filmGenre.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("filmDbStorage")
@Slf4j
@Repository
public class FilmDbStorage extends BaseRepository<Film> implements FilmStorage {

    private static final String FIND_ALL_QUERY = "SELECT f.*, m.id AS mpa_id, m.name AS mpa_name FROM films f " +
            "LEFT JOIN mpa m ON f.mpa_id = m.id";
    private static final String INSERT_QUERY = "INSERT INTO films (name, release_date, description, " +
            "duration, mpa_id, rate) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, release_date = ?, description = ?, " +
            "duration = ?, mpa_id = ?, rate = ? WHERE id = ?";

    private final FilmGenreStorage filmGenreStorage;
    private final MpaStorage mpaStorage;
    private final LikeStorage likeStorage;

    public FilmDbStorage(JdbcTemplate jdbc,
                         RowMapper<Film> mapper,
                         FilmGenreStorage filmGenreStorage,
                         MpaStorage mpaStorage, LikeStorage likeStorage) {
        super(jdbc, mapper);
        this.filmGenreStorage = filmGenreStorage;
        this.mpaStorage = mpaStorage;
        this.likeStorage = likeStorage;
    }

    @Override
    public Film addFilm(Film film) {
        Integer id = Math.toIntExact(insert(
                INSERT_QUERY,
                film.getName(),
                film.getReleaseDate(),
                film.getDescription(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getRate()
        ));
        film.setId(id);
        return addFields(film);
    }

    @Override
    public Film updateFilm(Film film) {
        filmGenreStorage.deleteAllFilmGenresByFilmId(film.getId());
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getReleaseDate(),
                film.getDescription(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getRate(),
                film.getId()
        );
        return addFields(film);
    }

    @Override
    public Film getByFilmId(Integer filmId) {
        Optional<Film> optionalFilm = findOne(FIND_ALL_QUERY.concat(" WHERE f.id = ?"), filmId);
        if (optionalFilm.isEmpty()) {
            throw new NotFoundException("Фильм под id=%s не найден");
        } else {
            return addFields(optionalFilm.get());
        }
    }

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> films = findMany(FIND_ALL_QUERY);
        return setMultipleFilmsFields(films);
    }

    @Deprecated
    @Override
    public Film addLike(Integer filmId, Integer userId) {
        return null;
    }

    @Deprecated
    @Override
    public void removeLike(Integer filmId, Integer userId) {

    }

    @Override
    public List<Film> getMostPopular(Integer count) {
        return null;
    }

    private Film addFields(Film film) {
        Integer filmId = film.getId();
        Integer mpaId = film.getMpa().getId();
        if (film.getGenres() != null) {
            film.getGenres().forEach(genre -> filmGenreStorage.addFilmGenre(filmId, genre.getId()));
        }
        List<Genre> filmGenres = (List<Genre>) filmGenreStorage.getAllFilmGenresByFilmId(film.getId());
        Mpa filmMpa = mpaStorage.getMpaById(mpaId).get();
        List<Like> filmLikes = (List<Like>) likeStorage.getLikesFilmId(filmId);
        return film.toBuilder().mpa(filmMpa).genres(filmGenres).likes(filmLikes).build();
    }

    private List<Film> setMultipleFilmsFields(Collection<Film> films) {
        Map<Integer, List<Genre>> filmGenresMap = filmGenreStorage.getAllFilmGenres(films);
        films.forEach(film -> {
            Integer filmId = film.getId();
            film.setGenres((List<Genre>) filmGenresMap.getOrDefault(filmId, new ArrayList<>()));
            film.setLikes(likeStorage.getLikesFilmId(filmId));
        });
        return (List<Film>) films;
    }
}
