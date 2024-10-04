package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exceptions.LikeException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addFilm(Film film) {
        checkFilmIsNotFound(film, film.getId());
        if (!FilmValidator.isValid(film))
            throw new ValidationException("Некорректные данные");
        filmStorage.addFilm(film);
        return film;
    }

    public Film updateFilm(Film film) {
        checkIsCreatedObject(film.getId());
        if (!FilmValidator.isValid(film))
            throw new ValidationException("Некорректные данные");
        filmStorage.updateFilm(film);
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public void addLike(Integer filmId, Integer userId) {
        checkIsCreatedObject(filmId, userId);
        if (filmStorage.getFilmById(filmId) == null)
            throw new NotFoundException("Нема такого");
        if (!filmStorage.getFilmById(filmId).getLikes().contains(userStorage.getUserById(userId))) {
            filmStorage.getFilmById(filmId).getLikes().add(userStorage.getUserById(userId));
        } else {
            throw new LikeException("Пользователь уже ставил лайк этому фильму");
        }
    }

    public void removeLike(Integer filmId, Integer userId) {
        checkIsCreatedObject(filmId, userId);
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
        checkIsCreatedObject(filmId);
        return filmStorage.getFilmById(filmId);
    }

    public Set<Film> getPopularFilms(Integer count) {
        Set<Film> popularFilms = new TreeSet<>();
        popularFilms.addAll(filmStorage.getFilms());
        if (count != null && popularFilms.size() >= count) {
            popularFilms.stream().limit(count).collect(Collectors.toSet());
        } else if (popularFilms.size() >= 10) {
            popularFilms.stream().limit(10).collect(Collectors.toSet());
        } else {
            popularFilms.stream().limit(popularFilms.size()).collect(Collectors.toSet());
        }
        return popularFilms;
    }

    private void checkFilmIsNotFound(Film film, Integer id) {
        if (FilmValidator.isFilmNull(film)) {
            throw new NotFoundException(String.format("Нема такого", id));
        }
    }

    private void checkIsCreatedObject(Integer filmId, Integer userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (Objects.isNull(user))
            throw new NotFoundException("Нет юзера");
        if (userStorage.getUserById(userId) == null)
            throw new NotFoundException("Нет юзера");
        checkFilmIsNotFound(film, filmId);
        if (filmStorage.getFilmById(filmId) == null)
            throw new NotFoundException("Нема такого");
    }

    private void checkIsCreatedObject(Integer filmId) {
        Film film = filmStorage.getFilmById(filmId);
        checkFilmIsNotFound(film, filmId);
        if (filmStorage.getFilmById(filmId) == null)
            throw new NotFoundException("Нема такого");
    }
}
