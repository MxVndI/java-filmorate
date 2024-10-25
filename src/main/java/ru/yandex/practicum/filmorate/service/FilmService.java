package ru.yandex.practicum.filmorate.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.time.LocalDate;
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
        validate(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        validate(film);
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public void addLike(@NonNull Integer filmId, @NonNull Integer userId) {
        likeStorage.addLike(filmId, userId);
    }

    public void removeLike(@NonNull Integer filmId, @NonNull Integer userId) {
        likeStorage.removeLike(filmId, userId);
    }

    public Film getFilmById(Integer filmId) {
        validate(filmStorage.getByFilmId(filmId));
        return filmStorage.getByFilmId(filmId);
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

    private void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Film name is null or blank");
            throw new ConditionsNotMetException("Имя не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.warn("Film description length is exceeding 200 chars limit");
            throw new ConditionsNotMetException("Описание не может быть длиннее 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1896, 12, 28))) {
            log.warn("Film release date is before 1869/12/28");
            throw new ConditionsNotMetException("Дата релиза не может быть раньше 28 декабря 1869 года");
        } else if (film.getDuration() < 0) {
            log.warn("Film duration value is negative");
            throw new ConditionsNotMetException("Продолжительность фильма должна быть положительным числом");
        }
    }

}
