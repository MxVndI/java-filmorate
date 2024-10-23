package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film getByFilmId(Integer filmId);

    Collection<Film> getFilms();

    Film addLike(Integer filmId, Integer userId);

    void removeLike(Integer filmId, Integer userId);

    List<Film> getMostPopular(Integer count);
}
