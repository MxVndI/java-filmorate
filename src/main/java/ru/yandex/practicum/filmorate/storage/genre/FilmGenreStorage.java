package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.Collection;

public interface FilmGenreStorage {
    void addGenre(String name);

    Collection<FilmGenre> getGenreById(Integer id);

    void deleteGenreById(Integer id);

    Collection<FilmGenre> getGenres();
}