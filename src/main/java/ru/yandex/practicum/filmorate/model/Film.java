package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Collection;

@Data
@EqualsAndHashCode(of = "name")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film implements Comparable<Film> {
    Integer id;
    String name;
    String description;
    LocalDate releaseDate;
    Integer duration;
    Collection<Like> likes;
    Collection<Genre> genres;
    Mpa mpa;
    Integer rate;

    @Override
    public int compareTo(Film other) {
        return Integer.compare(other.getLikes().size(), this.getLikes().size());
    }
}
