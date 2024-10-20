package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.storage.genre.FilmGenreStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final FilmGenreStorage genreStorage;

    public FilmGenre getGenreById(Integer id) {
        return genreStorage.getGenreById(id);
    }

    public Collection<FilmGenre> getGenres() {
        return genreStorage.getGenres();
    }
}