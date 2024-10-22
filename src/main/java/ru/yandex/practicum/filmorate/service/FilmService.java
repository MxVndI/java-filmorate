package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;

    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("likeDbStorage") LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
    }

    public Film addFilm(Film film) {
        return filmStorage.save(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public void addLike(Integer filmId, Integer userId) {
        likeStorage.addLike(filmId, userId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        likeStorage.removeLike(filmId, userId);
    }

    public Film getFilmById(Integer filmId) {
        return filmStorage.getById(filmId);
    }

    public Set<Film> getPopularFilms(Integer count) {
        Set<Film> popularFilms = new TreeSet<>();
        popularFilms.addAll(filmStorage.getFilms());
        if (count != null && popularFilms.size() >= count.intValue()) {
            popularFilms.stream().limit(count).collect(Collectors.toSet());
        } else if (popularFilms.size() >= 10) {
            popularFilms.stream().limit(10).collect(Collectors.toSet());
        } else {
            popularFilms.stream().limit(popularFilms.size()).collect(Collectors.toSet());
        }
        return popularFilms;
    }

}
