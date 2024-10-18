package ru.yandex.practicum.filmorate.model;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    public Film addFilm(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        films.replace(film.getId(), film);
        return film;
    }

    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Film getFilmById(Integer filmId) {
        return films.get(filmId);
    }

    private Integer getNextId() {
        Integer currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
