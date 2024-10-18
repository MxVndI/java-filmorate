package ru.yandex.practicum.filmorate.model;

import lombok.Setter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class Film implements Comparable<Film> {
    private int id = 0;
    @NonNull
    private final String name;
    @NonNull
    private final String description;
    @NonNull
    private final LocalDate releaseDate;
    @NonNull
    private final int duration;
    private Set<Integer> likes = new HashSet<>();

    public int getCountLikes() {
        return likes.size();
    }

    @Override
    public int compareTo(Film other) {
        return Integer.compare(other.getCountLikes(), this.getCountLikes());
    }
}