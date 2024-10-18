package ru.yandex.practicum.filmorate.filmComporator;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class FilmLikesComporator implements Comparator<Film> {
    @Override
    public int compare(Film o1, Film o2) {
        if (o1.getLikes().size() > o1.getLikes().size()) return 1;
        else if (o1.getLikes().size() == o2.getLikes().size()) return 0;
        else return -1;
    }
}
