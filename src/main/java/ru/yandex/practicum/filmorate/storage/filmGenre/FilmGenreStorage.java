package ru.yandex.practicum.filmorate.storage.filmGenre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Map;

public interface FilmGenreStorage {

    void addFilmGenre(Integer filmId, Integer genreId);

    Collection<Genre> getAllFilmGenresByFilmId(Integer filmId);

    void deleteAllFilmGenresByFilmId(Integer filmId);

    Map<Integer, Collection<Genre>> getAllFilmGenres(Collection<Film> films);
}
