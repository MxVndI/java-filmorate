package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        validate(film);
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        validate(film);
        return filmService.updateFilm(film);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable("id") Integer filmId, @PathVariable("userId") Integer userId) {
        validate(filmId, userId);
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") Integer filmId, @PathVariable("userId") Integer userId) {
        validate(filmId, userId);
        filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(name = "count", defaultValue = "10", required = false) Integer count) {
        return filmService.getPopularFilms(count);
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable("filmId") Integer filmId) {
        validate(filmService.getFilmById(filmId));
        return filmService.getFilmById(filmId);
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

    private void validate(Integer filmId, Integer userId) {
        if (filmId == null) {
            log.warn("filmId is null");
            throw new ConditionsNotMetException("Не указан id фильма.");
        } else if (userId == null) {
            log.warn("userId is null");
            throw new ConditionsNotMetException("Не указан id пользователя.");
        }
    }
}
