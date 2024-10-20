package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class Film implements Comparable<Film> {
    private int id = 0;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
    private final Rating rating;
    private final FilmGenre genre;
    private Set<Integer> likes = new HashSet<>();

    public int getCountLikes() {
        return likes.size();
    }

    @Override
    public int compareTo(Film other) {
        return Integer.compare(other.getCountLikes(), this.getCountLikes());
    }
}