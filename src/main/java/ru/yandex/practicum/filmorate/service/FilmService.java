package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exceptions.LikeException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addFilm(Film film) {
        if (FilmValidator.isValid(film)) {
            filmStorage.addFilm(film);
        } else {
            throw new ValidationException("Непредвиденная ошибка..");
        }
        return film;
    }

    public Film updateFilm(Film film) {
        if (FilmValidator.isValid(film)) {
            filmStorage.updateFilm(film);
            return film;
        } else {
            throw new ValidationException("Фильма с таким id не существует");
        }

    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public void addLike(Integer filmId, Integer userId) {
        if (!filmStorage.getFilmById(filmId).getLikes().contains(userStorage.getUserById(userId))) {
            filmStorage.getFilmById(filmId).getLikes().add(userStorage.getUserById(userId));
        } else {
            throw new LikeException("Пользователь уже ставил лайк этому фильму");
        }
    }

    public void removeLike(Integer filmId, Integer userId) {
        if (filmStorage.getFilmById(filmId).getLikes().contains(userStorage.getUserById(userId))) {
            filmStorage.getFilmById(filmId).getLikes().remove(userStorage.getUserById(userId));
        } else {
            throw new LikeException("Пользователь не ставил лайк этому фильму");
        }
    }

    public int getLikesCount(Film film) {
        return film.getLikes().size();
    }

    public Film getFilmById(Integer filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public Set<Film> getPopularFilms(Integer count) {
        Set<Film> popularFilms = new TreeSet<>();
        popularFilms.addAll(filmStorage.getFilms());
        if (count != null) {
            popularFilms.stream().limit(count).collect(Collectors.toSet());
        } else {
            popularFilms.stream().limit(10).collect(Collectors.toSet());
        }
        return popularFilms;
    }

}
